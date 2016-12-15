package agrendalath.AOC2016;

import agrendalath.helpers.Input;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day11 {
    private static final int NUMBER_OF_FLOORS = 4;

    private static State parseInput(String input) {
        Pattern microchip = Pattern.compile("\\w+-");
        Pattern generator = Pattern.compile("\\w+ generator");
        State state = new State();

        for (String line : input.split("\n")) {
            Floor floor = new Floor();
            state.add(floor);

            Matcher microchips = microchip.matcher(line);
            Matcher generators = generator.matcher(line);

            while (microchips.find())
                floor.addMicrochip(microchips.group().replaceAll("-", ""));

            while (generators.find())
                floor.addGenerator(generators.group().replaceAll(" .*", ""));
        }

        return state;
    }

    static int solveFirst(String input) {
        State currentState = parseInput(input);
        Set<State> previousStates = new HashSet<>();

        previousStates.add(new State(currentState));

        Queue<State> queue = new ArrayDeque<>();
        queue.add(currentState);

        while (!queue.isEmpty()) {
            currentState = queue.remove();
            for (State state : currentState.getNextStates(previousStates)) {
                if (state.isWinning())
                    return state.getMoves();

                previousStates.add(state);
                queue.add(state);

//                }
            }
        }
        return -1;
    }

    static int solveSecond(String input) {
        //noinspection SpellCheckingInspection
        return solveFirst(input.replaceFirst("\\.", ", an elerium generator, an elerium-compatible microchip, " +
                "a dilithium generator, a dilithium-compatible microchip."));
    }

    public static void main(String[] args) {
        System.out.println(solveFirst(Input.get(Day11.class)));

        /* Computed in 15 minutes and 55 seconds when with: -Xmx10g -XX:-UseGCOverheadLimit -XX:+UseConcMarkSweepGC */
        System.out.println(solveSecond(Input.get(Day11.class)));
    }

    private static class State {
        private Floor[] floors = new Floor[NUMBER_OF_FLOORS];
        private int position = 0;
        private int moves = 0;

        State() {
        }

        State(State state) {
            for (int i = 0; i < floors.length; ++i) {
                floors[i] = new Floor(state.floors[i]);
            }
            position = state.position;
            moves = state.moves + 1;
        }

        void add(Floor floor) {
            for (int i = 0; i < floors.length; ++i) {
                if (floors[i] == null) {
                    floors[i] = floor;
                    return;
                }
            }
        }

        Collection<State> getNextStates(Set<State> previousStates) {
            //noinspection unchecked
            Set<State> nextStates = new StateHashSet(previousStates);
            moveGenerators(nextStates);
            moveMicrochips(nextStates);
            movePairs(nextStates);

            return nextStates;
        }

        void moveGenerators(Set<State> nextStates) {
            floors[position].generatorsOnly.forEach(generator -> {
                if (position - 1 >= 0) {
                    State newState = moveGenerator(this, generator, position, position - 1);
                    nextStates.add(newState);

                    if (!newState.isLegal()) {
                        newState.floors[position].generatorsOnly.forEach(secondGenerator ->
                                nextStates.add(moveGenerator(newState, secondGenerator, position, position - 1)));
                        newState.floors[position].microchipsOnly.forEach(secondMicrochip ->
                                nextStates.add(moveMicrochip(newState, secondMicrochip, position, position - 1)));
                    }
                }
                if (position + 1 < floors.length) {
                    State newState = moveGenerator(this, generator, position, position + 1);
                    int numberOfStates = nextStates.size();

                    newState.floors[position].generatorsOnly.forEach(secondGenerator ->
                            nextStates.add(moveGenerator(newState, secondGenerator, position, position + 1)));
                    newState.floors[position].microchipsOnly.forEach(secondMicrochip ->
                            nextStates.add(moveMicrochip(newState, secondMicrochip, position, position + 1)));

                    if (nextStates.size() == numberOfStates)
                        nextStates.add(newState);
                }
            });
        }

        void moveMicrochips(Set<State> nextStates) {
            Set<String> pairs = new HashSet<>(floors[position].generators);
            pairs.retainAll(floors[position].microchips);
            String[] pairsArray = pairs.toArray(new String[0]);
            String pair = pairsArray.length > 0 ? pairsArray[0] : null;

            floors[position].microchipsOnly.forEach(microchip -> {
                if (position - 1 >= 0) {
                    State newState = moveMicrochip(this, microchip, position, position - 1);
                    nextStates.add(newState);

                    if (!newState.isLegal()) {
                        newState.floors[position].microchipsOnly.forEach(secondMicrochip ->
                                nextStates.add(moveMicrochip(newState, secondMicrochip, position, position - 1)));
                        if (pair != null) {
                            if (newState.floors[position].canUnpairGenerator() == 1)
                                nextStates.add(moveGenerator(newState, pair, position, position - 1));
                            nextStates.add(moveMicrochip(newState, pair, position, position - 1));
                        }
                    }
                }
                if (position + 1 < floors.length) {
                    State newState = moveMicrochip(this, microchip, position, position + 1);
                    int numberOfStates = nextStates.size();

                    newState.floors[position].microchipsOnly.forEach(secondMicrochip ->
                            nextStates.add(moveMicrochip(newState, secondMicrochip, position, position + 1)));
                    if (pair != null) {
                        if (newState.floors[position].canUnpairGenerator() == 1)
                            nextStates.add(moveGenerator(newState, pair, position, position + 1));
                        nextStates.add(moveMicrochip(newState, pair, position, position + 1));
                    }

                    if (nextStates.size() == numberOfStates)
                        nextStates.add(newState);
                }
            });
        }

        void movePairs(Set<State> nextStates) {
            Set<String> pairs = new HashSet<>(floors[position].generators);
            pairs.retainAll(floors[position].microchips);
            String[] pairsArray = pairs.toArray(new String[0]);
            if (pairsArray.length == 0)
                return;

            String pair = pairsArray[0];
            String secondPair = pairsArray.length > 1 ? pairsArray[1] : null;

            if (position - 1 >= 0)
                nextStates.add(movePair(pair, position, position - 1));

            if (position + 1 < floors.length)
                nextStates.add(movePair(pair, position, position + 1));

            if (position - 1 >= 0) {
                State newState = moveMicrochip(this, pair, position, position - 1);
                nextStates.add(newState);

                if (!newState.isLegal()) {
                    newState.floors[position].microchipsOnly.forEach(microchip ->
                            nextStates.add(moveMicrochip(newState, microchip, position, position - 1)));
                    if (secondPair != null) {
                        nextStates.add(moveMicrochip(newState, secondPair, position, position - 1));
                    }
                }
            }
            if (position + 1 < floors.length) {
                State newState = moveMicrochip(this, pair, position, position + 1);
                int numberOfStates = nextStates.size();

                newState.floors[position].microchipsOnly.forEach(microchip ->
                        nextStates.add(moveMicrochip(newState, microchip, position, position + 1)));
                if (secondPair != null) {
                    nextStates.add(moveMicrochip(newState, secondPair, position, position + 1));
                }

                if (nextStates.size() == numberOfStates)
                    nextStates.add(newState);
            }

            if (floors[position].canUnpairGenerator() != 0) {
                if (position - 1 >= 0) {
                    State newState = moveGenerator(this, pair, position, position - 1);
                    if (floors[position].canUnpairGenerator() == 1)
                        nextStates.add(newState);
                    else {
                        newState.floors[position].generatorsOnly.forEach(generator ->
                                nextStates.add(moveGenerator(newState, generator, position, position - 1)));
                        newState.floors[position].microchipsOnly.forEach(microchip ->
                                nextStates.add(moveMicrochip(newState, microchip, position, position - 1)));
                        if (secondPair != null)
                            nextStates.add(moveGenerator(newState, secondPair, position, position - 1));
                    }
                }
                if (position + 1 < floors.length) {
                    State newState = moveGenerator(this, pair, position, position + 1);
                    if (floors[position].canUnpairGenerator() == 1)
                        nextStates.add(newState);
                    else {
                        newState.floors[position].generatorsOnly.forEach(generator ->
                                nextStates.add(moveGenerator(newState, generator, position, position + 1)));
                        newState.floors[position].microchipsOnly.forEach(microchip ->
                                nextStates.add(moveMicrochip(newState, microchip, position, position + 1)));
                        if (secondPair != null)
                            nextStates.add(moveGenerator(newState, secondPair, position, position + 1));
                    }
                }

            }
        }

        private State moveGenerator(State state, String item, int from, int to) {
            State newState = new State(state);
            if (state != this)
                --newState.moves;
            newState.position = to;
            newState.floors[from].getGenerator(item);
            newState.floors[to].addGenerator(item);
            return newState;
        }

        private State moveMicrochip(State state, String item, int from, int to) {
            State newState = new State(state);
            if (state != this)
                --newState.moves;
            newState.position = to;
            newState.floors[from].getMicrochip(item);
            newState.floors[to].addMicrochip(item);
            return newState;
        }

        private State movePair(String item, int from, int to) {
            State newState = new State(this);
            newState.position = to;
            newState.floors[from].getGenerator(item);
            newState.floors[from].getMicrochip(item);
            newState.floors[to].addGenerator(item);
            newState.floors[to].addMicrochip(item);
            return newState;
        }

        Floor get(int i) {
            return floors[i];
        }

        int getMoves() {
            return moves;
        }

        boolean isLegal() {
            for (Floor floor : floors) {
                Set<String> microchipsOnly = new HashSet<>(floor.microchips);
                microchipsOnly.removeAll(floor.generators);
                if (!(microchipsOnly.size() == 0 || floor.generators.size() == 0))
                    return false;
            }
            return true;
        }

        boolean isWinning() {
            for (int i = 0; i < floors.length - 1; ++i)
                if (!floors[i].isEmpty())
                    return false;

            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            State state = (State) o;
            return Arrays.equals(floors, state.floors);
        }

        @Override
        public int hashCode() {
            return 31 * Arrays.hashCode(floors) + position;
        }
    }

    private static class Floor {
        private final Set<String> generators;
        private final Set<String> microchips;

        private final Set<String> generatorsOnly;
        private final Set<String> microchipsOnly;

        Floor() {
            generators = new HashSet<>();
            microchips = new HashSet<>();
            generatorsOnly = new HashSet<>();
            microchipsOnly = new HashSet<>();
        }

        Floor(Floor floor) {
            generators = new HashSet<>(floor.generators);
            microchips = new HashSet<>(floor.microchips);
            generatorsOnly = new HashSet<>(floor.generatorsOnly);
            microchipsOnly = new HashSet<>(floor.microchipsOnly);
        }

        void addGenerator(String item) {
            generators.add(item);
            if (microchips.contains(item))
                microchipsOnly.remove(item);
            else
                generatorsOnly.add(item);
        }

        void addMicrochip(String item) {
            microchips.add(item);
            if (generators.contains(item))
                generatorsOnly.remove(item);
            else
                microchipsOnly.add(item);
        }

        void getGenerator(String item) {
            generators.remove(item);
            generatorsOnly.remove(item);
            if (microchips.contains(item))
                microchipsOnly.add(item);
        }

        void getMicrochip(String item) {
            microchips.remove(item);
            microchipsOnly.remove(item);
            if (generators.contains(item))
                generatorsOnly.add(item);
        }

        int canUnpairGenerator() {
            if (generators.size() == 1 || generators.size() == 2)
                return generators.size();
            return 0;
        }

        boolean isEmpty() {
            return generators.size() == 0 && microchips.size() == 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            Floor floor = (Floor) o;

            return generatorsOnly.equals(floor.generatorsOnly) && microchipsOnly.equals(floor.microchipsOnly) &&
                    generators.size() == floor.generators.size();
        }

        @Override
        public int hashCode() {
            int result = generators.size();
            result = 31 * result + microchips.size();
            return result;
        }
    }

    private static class StateHashSet implements Set {
        private final Set<State> set = new HashSet<>();
        private final Set<State> previousStates;

        StateHashSet(Set<State> previousStates) {
            this.previousStates = previousStates;
        }

        @Override
        public int size() {
            return set.size();
        }

        @Override
        public boolean isEmpty() {
            return set.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return set.contains(o);
        }

        @Override
        public Iterator iterator() {
            return set.iterator();
        }

        @Override
        public Object[] toArray() {
            return set.toArray();
        }

        @Override
        public boolean add(Object o) {
            //noinspection SuspiciousMethodCalls
            return (((State) o).isLegal() && !previousStates.contains(o) && set.add((State) o));
        }

        @Override
        public boolean remove(Object o) {
            return set.remove(o);
        }

        @Override
        public boolean addAll(Collection collection) {
            //noinspection unchecked
            return set.addAll((Collection<? extends State>) collection);
        }

        @Override
        public void clear() {
            set.clear();
        }

        @Override
        public boolean retainAll(Collection collection) {
            return set.retainAll(collection);
        }

        @Override
        public boolean removeAll(Collection collection) {
            //noinspection unchecked
            return set.removeAll((Collection<? extends State>) collection);
        }

        @Override
        public boolean containsAll(Collection collection) {
            return set.containsAll(collection);
        }

        @Override
        public Object[] toArray(Object[] objects) {
            return set.toArray(new Object[0]);
        }
    }
}
