
import java.util.Stack;
import java.util.Scanner;
import java.util.EmptyStackException;

/**
 * This class contains an array of arithmetic expression and can 
 * convert infix to postfix and vice versa.
 * @author Karanveer Sandhu
 */
public class Arithmetic {
    
    // An arithmetic expression in infix
    private String expression;
    
   /**
    * Converts the String so that the String will have no white spaces.
    * @param expression -the arithmetic expression.
    * @return the converted String without any white spaces.
    */
    private static String noWhiteSpaces(String expression)
    {
        String output = expression; // the output will be the original string
        
        // throughout the string.
        for (int i = 0; i < output.length(); i++)
        {
            String a = output.substring(i, i+1); // each character
            if (a.equals(" ")) // if it is a space
            {
                // the string will be everthing but that space
                output = output.substring(0, i) + output.substring(i+1);
                // decrement i so we don't skip any characters on the string
                i--; 
            }
        } // end of for loop
        
        return output;
    } // end of noWhiteSpaces()
    
    /**
     * Checks if operator a has higher than or equal to precedence than 
     * operator b.
     * @param a the first operator
     * @param b the second operator
     * @return whether or not operator a had a higher than or equal to
     * precedence than operator b.
     */
    private static boolean isHigherPrecedence(String a, String b)
    {
        if (a.equals("%") || a.equals("/") || a.equals("*"))
        {
            return true;
        }
        else if (a.equals("+") || a.equals("-"))
        {
            if (b.equals("+") || b.equals("-"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    } // end of isHigherPrecedence()
    
    /**
     * Determines if this string is a number.
     * @param num the string to test if it is a number
     * @return whether the string is a number or not.
     */
    private static boolean isNumber(String num)
    {
        try 
        {
            Integer.parseInt(num);
            return true;    
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        
    }
    
    /**
     * Determines if this string is one of the operators +, -, *, /, or %.
     * @param operator the string to test if it is an operator
     * @return whether it is an operator or not.
     */
    private static boolean isOperator(String operator)
    {
        return ("+-*/%".contains(operator));
    }
    
    /**
     * Gets three strings and evaluate the operation that is there.
     * @param t1 - the first number/operand
     * @param op - the operator
     * @param t2 - the second number/operand
     * @return the value of the evaluation of the operation.
     */
    private static String doOperation(String t1, String op, String t2)
    {
        // This ordering is used because of how the stack pops up the
        // 2ND operand FIRST.
        int num2 = Integer.parseInt(t1);
        int num1 = Integer.parseInt(t2);
        
        if (op.equals("+"))
        {
            return (num1 + num2) + "";
        }
        else if (op.equals("-"))
        {
            return (num1 - num2) + "";
        }
        else if (op.equals("*"))
        {
            return (num1 * num2) + "";
        }
        else if (op.equals("/"))
        {
            return (num1 / num2) + "";
        }
        else // %
        {
            return (num1 % num2) + "";
        }
    } // end of doOperation()
    
    /**
     * Creates an Arithmetic Object
     * @param expression the array of arithmetic expressions
     */
    public Arithmetic(String expression)
    {
        this.expression = expression;
    }
    
    /**
     * Determines whether the arithmetic expression is properly written
     * regarding parenthesis.
     * @return whether or not the infix expression is properly written
     * regarding parenthesis.
     */
    public boolean isBalance()
    {
        Stack<Object> stack = new Stack<>();
        
        String math = noWhiteSpaces(expression); // remove the white spaces
        
        // Throughout the whole expression
        for (int i = 0; i < math.length(); i++)
        {
            String a = math.substring(i, i+1); // the current char
            if (a.equals("(")) // if current char is (
            {
                stack.push("("); // push on stack
            }
            if (a.equals(")")) // if it is )
            {
                if (stack.empty()) // then first check if stack is empty
                {
                    return false; // if it is, then it is not balanced
                }
                else
                {
                    stack.pop(); // otherwise remove a ( from the stack
                } // end of second if-else statements
            } // end of first set of  if statments
        } // end of for loop
        
        if (!stack.empty()) // if the stack is not empty
        {
            return false; // expression was not balanced
        }
        else // otherwise
        {
            return true; // it is balanced.
        } // end of if statement
    } // end isBalanced()
    
    /**
     * Converts the arithmetic expression from infix into postfix
     */
    public void postFixExpression()
    {
        Stack<Object> stack = new Stack<>();
        
        String postFix = ""; // the postfix expression or the output string
        
        // used to find things in the expression (that are not spaces)
        // Since numbers may be multiple characters.
        Scanner scan = new Scanner(expression);
        
        // while there's more to the 
        while (scan.hasNext())
        {
            // could be a number, operator or parenthesis.
            String a = scan.next(); 
            if (isNumber(a)) // if that's a #
            {
                postFix += a + " "; // write to output str.
            }
            else if (a.equals("(")) // if it's (
            {
                stack.push("("); // push to stack
            }
            else if (a.equals(")")) // if it's )
            {
                // keep popping until the first ( and put all that on
                // output string.
                while (!(stack.peek().equals("(")))
                {
                    postFix += stack.pop() + " ";
                } // end of while
                
                stack.pop(); // discard the (
            }
            else // it's an operator
            {
                // if op on top of stack has higher/equal precedence than
                // scanned string and we didn't encounter a (
                while (!stack.empty() && !(stack.peek().equals("(")) && 
                        isHigherPrecedence((String)stack.peek(), a))
                {
                    // pop that operator and put it in output string
                    postFix += stack.pop() + " ";
                } // after the while
                
                // if we stopped due to lower precedence op or stack being empty
                if (stack.empty() || 
                        !isHigherPrecedence((String)stack.peek(), a)) 
                {
                    stack.push(a); // put scanned operator on stack.
                }
                else // we stopped due to (
                {
                    postFix += stack.pop() + " ";
                    stack.push(a); // just push the scanned operator
                } // end of if-else statements
            } // end of if-else chain of scanned token.
        } // end of while statement and we reached the end of expression.
        
        while (!stack.empty()) // while the stack is not empty
        {
            postFix += stack.pop() + " "; // Pop em all to the output string.
        } 
        
        expression = postFix; // change the instance variable.
    } // end of postFixExpression()
    
    /**
     * Evaluates the expression if it is in postfix 
     * @return the evaluation of the expression
     */
    public String evaluateRPN()
    {
        Stack<Object> stack = new Stack<>(); // stack to hold operands
        
        String success = "";
        String failure = "This expression is malformed and cannot be"
                + " evaluated.";
        
        
        // to get tokens from postfix expression.
        Scanner scan = new Scanner(expression); 
        
        // while there's more to the postfix expression...
        while (scan.hasNext())
        {
            String a = scan.next(); // next token
            
            if (isNumber(a)) // if said token is a number
            {
                stack.push(a); // push number on stack
            }
            else if (isOperator(a)) // if it is an operator
            {
                try // first, watch out for the EmptyStackException
                {
                    // pop the first two operands and evaluate.
                    String value = doOperation((String)stack.pop(), a, 
                            (String)stack.pop());
                    // then push the value into the stack.
                    stack.push(value);
                }
                catch (EmptyStackException e)
                {
                    return failure; // the "error" message
                }
            }
            else // if the token was anything else.
            {
                return failure; // the expression was malformed.
            } // end of if-else statements
        } // end of while so the postfix expression is no more.
        
        // Supposedly the stack has the final answer and ONLY the final answer.
        if (stack.empty()) // if the stack didn't have anything
        {
            return failure; // expression was malformed
        }
        
        success += stack.pop(); // get that final value.
        
        if (stack.empty()) // if it is now empty
        {
            // we got the value.
            return success + " is the postFix evaluation";
        }
        else // otherwise
        {
            return failure; // it was a malformed expression.
        } // end of if-else statements
    } // end of evaluateRPN()
    
    /**
     * An accessor for the expression instance variable
     * @return the expression instance variable
     */
    public String getExpression()
    {
        return expression;
    }
}
