

This page is currently being reconstructed.

## Introduction ##
Conditionals has been overhauled, specifically:

  * Parenthesis `()` are no longer required around the expression.
  * The `case` keyword is removed in the `switch` statement.
  * `break` is the default behaviour in a `case` block and can be reversed by the new keyword `fallthru`.
  * `when` is introduced as a postfix `if` sentence.
  * `elseif` and `else if` has been changed to `elif`.

## Matrix ##
<table cellpadding='5' border='1'>
<blockquote><tr>
<blockquote><td><strong>Snow</strong></td>
<td><strong>PHP</strong></td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>if a &gt; 0<br>
    echo "a is positive."<br>
elif a == 0<br>
    echo "a is zero."<br>
else<br>
    echo "a is negative."<br>
    <br>
# Or:<br>
<br>
if a &gt; 0: echo "a is positive."<br>
elif a == 0: echo "a is zero."<br>
else: echo "a is negative."<br>
</code></pre>
</td>
<td>
<pre><code>if ($a &gt; 0) {<br>
    echo "a is positive.";<br>
} elseif ($a == 0) {<br>
    echo "a is zero.";<br>
} else {<br>
    echo "a is negative.";<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<blockquote><tr>
<td>
<pre><code>if (a_very_long_variable_name-&gt;and_a_very_long_function_name and<br>
                    also_a_very_long_variable_name-&gt;normal_func_name)<br>
    pass<br>
</code></pre>
</td>
<td>
<pre><code>if (and_a_very_long_function_name and($a_very_long_variable_name) &amp;&amp;<br>
normal_func_name($also_a_very_long_variable_name)) {<br>
    // Stub.<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<blockquote><tr>
</blockquote><blockquote><td>
<pre><code>a = switch foo<br>
    'bar': &lt;- 32<br>
    'baz': fallthru<br>
    'boz': &lt;- 42<br>
    default: &lt;- 52<br>
</code></pre>
</td>
<td>
<pre><code>switch ($foo) {<br>
    case 'bar': <br>
        $a = 32;<br>
        break;<br>
    case 'baz':<br>
    case 'boz':<br>
        $a = 42;<br>
    default:<br>
        $a = 52;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>a = b when c = d<br>
</code></pre>
</td>
<td>
<pre><code>if ($c = $d) {<br>
    $a = $b;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
</table></blockquote>

## switch ##
The following is an example of using switch in Snow:
```
switch myvar
    0
        echo "a is 0".
    -1
        fallthru
    1
        echo "a is -1 or 1.".
    'shortform': echo "a is 'shortform'."
    default
        echo "a is neither 0, -1 or 1."
```
Notice that the case keyword has been removed.

Also new is that each case statement per default has an implied `break` statement
added in the end. To revert this behaviour use the `fallthru` keyword, it's
effect is exactly the opposite of `break`. Instead of specifying where to stop
evaluation of the following statements, you tell the parser explicitly to 'fall
through' to the next `case`. The `break` keyword can still be used normally,
e.g. to break early in a condition.

```
# Example #1:
a = 1
switch a
    0
        echo 0
    1
        echo 1
    2
        echo 2
# prints: 1
```
```
# Example #2:
a = 1
switch a
    0
        echo 0
        fallthru
    1
        echo 1
        fallthru
    2
        echo 2
        fallthru
# prints: 12
```

## When ##
Snow introduces a new keyword `when` that works as a postfix `if` sentence.