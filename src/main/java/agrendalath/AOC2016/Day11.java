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
            for (State state : currentState.getNextStates()) {
                if (!previousStates.contains(state) && state.isLegal()) {
                    if (state.isWinning())
                        return state.getMoves();

                    previousStates.add(state);
                    queue.add(state);

                }
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

        Collection<State> getNextStates() {
            Set<String> generatorsOnly = new HashSet<>(floors[position].generators);
            generatorsOnly.removeAll(floors[position].microchips);
            Set<String> microchipsOnly = new HashSet<>(floors[position].microchips);
            microchipsOnly.removeAll(floors[position].generators);

            Set<State> nextStates = new HashSet<>();
            nextStates.addAll(moveGenerators(generatorsOnly, microchipsOnly));
            nextStates.addAll(moveMicrochips(microchipsOnly));
            nextStates.addAll(movePair(generatorsOnly, microchipsOnly));

            nextStates.remove(null);
            return nextStates;
        }

        Collection<State> moveGenerators(Set<String> generatorsOnly, Set<String> microchipsOnly) {
            Set<State> states = new HashSet<>();

            generatorsOnly.forEach(generator -> {
                if (position - 1 >= 0) {
                    State newState = moveGenerator(this, generator, position, position - 1);
                    states.add(newState);

                    generatorsOnly.forEach(secondGenerator -> {
                        if (!secondGenerator.equals(generator))
                            states.add(moveGenerator(newState, secondGenerator, position, position - 1));
                    });
                    microchipsOnly.forEach(secondMicrochip ->
                            states.add(moveMicrochip(newState, secondMicrochip, position, position - 1)));
                }
                if (position + 1 < floors.length) {
                    State newState = moveGenerator(this, generator, position, position + 1);
                    states.add(newState);

                    generatorsOnly.forEach(secondGenerator -> {
                        if (!secondGenerator.equals(generator))
                            states.add(moveGenerator(newState, secondGenerator, position, position + 1));
                    });
                    microchipsOnly.forEach(secondMicrochip ->
                            states.add(moveMicrochip(newState, secondMicrochip, position, position + 1)));
                }
            });
            return states;
        }

        Collection<State> moveMicrochips(Set<String> microchipsOnly) {
            Set<State> states = new HashSet<>();
            Set<String> pairs = new HashSet<>(floors[position].generators);
            pairs.retainAll(floors[position].microchips);
            String[] pairsArray = pairs.toArray(new String[0]);
            String pair = pairsArray.length > 0 ? pairsArray[0] : null;

            microchipsOnly.forEach(microchip -> {
                if (position - 1 >= 0) {
                    State newState = moveMicrochip(this, microchip, position, position - 1);
                    states.add(newState);

                    microchipsOnly.forEach(secondMicrochip -> {
                        if (!secondMicrochip.equals(microchip))
                            states.add(moveMicrochip(newState, secondMicrochip, position, position - 1));
                    });
                    if (pair != null) {
                        if (newState.floors[position].canUnpairGenerator() == 1)
                            states.add(moveGenerator(newState, pair, position, position - 1));
                        states.add(moveMicrochip(newState, pair, position, position - 1));
                    }
                }
                if (position + 1 < floors.length) {
                    State newState = moveMicrochip(this, microchip, position, position + 1);
                    states.add(newState);

                    microchipsOnly.forEach(secondMicrochip -> {
                        if (!secondMicrochip.equals(microchip))
                            states.add(moveMicrochip(newState, secondMicrochip, position, position + 1));
                    });
                    if (pair != null) {
                        if (newState.floors[position].canUnpairGenerator() == 1)
                            states.add(moveGenerator(newState, pair, position, position + 1));
                        states.add(moveMicrochip(newState, pair, position, position + 1));
                    }
                }
            });

            return states;
        }

        Collection<State> movePair(Set<String> generatorsOnly, Set<String> microchipsOnly) {
            Set<State> states = new HashSet<>();
            Set<String> pairs = new HashSet<>(floors[position].generators);
            pairs.retainAll(floors[position].microchips);
            String[] pairsArray = pairs.toArray(new String[0]);
            if (pairsArray.length == 0)
                return states;

            String pair = pairsArray[0];
            String secondPair = pairsArray.length > 1 ? pairsArray[1] : null;

            if (position - 1 >= 0)
                states.add(movePair(pair, position, position - 1));

            if (position + 1 < floors.length)
                states.add(movePair(pair, position, position + 1));

            if (position - 1 >= 0) {
                State newState = moveMicrochip(this, pair, position, position - 1);
                states.add(newState);

                microchipsOnly.forEach(microchip ->
                        states.add(moveMicrochip(newState, microchip, position, position - 1)));
                if (secondPair != null) {
                    states.add(moveMicrochip(newState, secondPair, position, position - 1));
                }
            }
            if (position + 1 < floors.length) {
                State newState = moveMicrochip(this, pair, position, position + 1);
                states.add(newState);

                microchipsOnly.forEach(microchip ->
                        states.add(moveMicrochip(newState, microchip, position, position + 1)));
                if (secondPair != null) {
                    states.add(moveMicrochip(newState, secondPair, position, position + 1));
                }
            }

            if (floors[position].canUnpairGenerator() != 0) {
                if (position - 1 >= 0) {
                    State newState = moveGenerator(this, pair, position, position - 1);
                    if (floors[position].canUnpairGenerator() == 1)
                        states.add(newState);
                    else {
                        generatorsOnly.forEach(generator ->
                                states.add(moveGenerator(newState, generator, position, position - 1)));
                        microchipsOnly.forEach(microchip ->
                                states.add(moveMicrochip(newState, microchip, position, position - 1)));
                        if (secondPair != null)
                            states.add(moveGenerator(newState, secondPair, position, position - 1));
                    }
                }
                if (position + 1 < floors.length) {
                    State newState = moveGenerator(this, pair, position, position + 1);
                    if (floors[position].canUnpairGenerator() == 1)
                        states.add(newState);
                    else {
                        generatorsOnly.forEach(generator ->
                                states.add(moveGenerator(newState, generator, position, position + 1)));
                        microchipsOnly.forEach(microchip ->
                                states.add(moveMicrochip(newState, microchip, position, position + 1)));
                        if (secondPair != null)
                            states.add(moveGenerator(newState, secondPair, position, position + 1));
                    }
                }

            }

            return states;
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

        Floor() {
            generators = new HashSet<>();
            microchips = new HashSet<>();
        }

        Floor(Floor floor) {
            generators = new HashSet<>(floor.generators);
            microchips = new HashSet<>(floor.microchips);
        }

        void addGenerator(String item) {
            generators.add(item);
        }

        void addMicrochip(String item) {
            microchips.add(item);
        }

        void getGenerator(String item) {
            generators.remove(item);
        }

        void getMicrochip(String item) {
            microchips.remove(item);
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

            return generators.equals(floor.generators) && microchips.equals(floor.microchips);
        }

        @Override
        public int hashCode() {
            int result = generators.size();
            result = 31 * result + microchips.size();
            return result;
        }
    }
}
