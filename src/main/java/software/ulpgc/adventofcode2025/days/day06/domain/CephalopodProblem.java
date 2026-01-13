package software.ulpgc.adventofcode2025.days.day06.domain;

import java.util.List;

/**
 * Represents a single math problem with numbers and an operator.
 *
 * This record encapsulates a math problem consisting of a list of numbers
 * and an operator to apply to them. The problem can solve itself by applying
 * the operator to all numbers in sequence. Being a record provides immutability
 * and automatic generation of constructors and accessors.
 *
 * @param numers List of numbers to be processed by the operator
 * @param operator The mathematical operator to apply (ADD or MULTIPLY)
 */
public record CephalopodProblem (List<Long> numers, Operator operator){
    /**
     * Solves the math problem by applying the operator to all numbers.
     *
     * Delegates to the operator's apply method, which uses the appropriate
     * operation (addition or multiplication) and identity value to reduce
     * the list of numbers to a single result.
     *
     * Examples:
     * - Numbers [5, 3, 2] with ADD operator → 5 + 3 + 2 = 10
     * - Numbers [5, 3, 2] with MULTIPLY operator → 5 × 3 × 2 = 30
     *
     * @return The result of applying the operator to all numbers
     */
    public long solve(){
        return operator.apply(numers);
    }

}