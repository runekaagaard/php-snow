

## Introduction ##
Snow provides several enhancements to both defining and calling functions:
  * The `function` keyword has been shortened to `fn`.
  * For function definitions without arguments the trailing `()` must be omitted.
  * Adds a new postfix `->` keyword for receivers functions.
  * Provides for supereasy argument validation.
  * Removes duplication of information between docblocks and function definition.

## Matrix ##
<table cellpadding='5' border='1'>
<blockquote><tr>
<blockquote><td><strong>Snow</strong></td>
<td><strong>PHP</strong></td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>fn foo<br>
    pass<br>
foo()<br>
</code></pre>
</td>
<td>
<pre><code>function foo() {<br>
    // Stub.<br>
}<br>
foo();<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>fn bar(x y)<br>
    pass<br>
</code></pre>
</td>
<td>
<pre><code>function bar($x, $y) {<br>
    // Stub.<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>@arr baz<br>
    # Argument comment.<br>
fn bar<br>
    # Function comment header.<br>
        Function comment body.<br>
    pass<br>
</code></pre>
</td>
<td>
<pre><code>/*<br>
 * Function comment header.<br>
 *<br>
 * Function comment body.<br>
 *<br>
 * @param array $baz<br>
 *   Argument comment.<br>
 */<br>
function bar($baz) {<br>
    <br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>fn foo()<br>
    pass<br>
</code></pre>
</td>
<td>ERROR. Trailing parenthesis must be omitted when the function does<br>
not have any arguments.<br>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>@int x<br>
    x &gt; 0<br>
fn bar<br>
    pass<br>
</code></pre>
</td>
<td>
<pre><code>/**<br>
 * @param int $x<br>
 */<br>
function bar($x) {<br>
    if (!($x &gt; 0)) {<br>
        throw new InvalidArgumentException();<br>
    }<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>@int x<br>
    # An integer bigger than 0.<br>
    0 &lt; _<br>
@int y<br>
    # An integer bigger than 0 and smaller than $x.<br>
    [0 &lt; _: '$y must be bigger than 0.'<br>
     _ &lt; x: '$y must be smaller than $x.'<br>
     is_foo(_): NotFooException("Not foo!")<br>
     is_y_ish(_): &lt;- false<br>
     is_crazy(_)]<br>
@return int<br>
def subtract<br>
    # Subtracts $y from $x.<br>
        Only works for positive returns.<br>
    &lt;- x - y<br>
</code></pre>
</td>
<td>
<pre><code>/**<br>
 * Subtracts $y from $x.<br>
 *<br>
 * Only works for positive returns.<br>
 *<br>
 * @param int $x<br>
 *   An integer bigger than 0.<br>
 * @param int $y<br>
 *   An integer bigger than 0 and smaller than $x.<br>
 * @return int<br>
 */<br>
function subtract($x, $y) {<br>
    if (!(0 &lt; $x)) {<br>
        throw new InvalidArgumentException();<br>
    }<br>
    if (!(0 &lt; $y)) {<br>
        throw new InvalidArgumentException(<br>
            '$y must be bigger than 0.');<br>
    }<br>
    if (!($y &lt; $x)) {<br>
        throw new InvalidArgumentException(<br>
            '$y must be smaller than $x.');<br>
    }<br>
    if (!is_foo($y)) {<br>
        throw new NotFooException("Not foo!");<br>
    }<br>
    if (!is_y_ish($y) {<br>
        return false;<br>
    }<br>
    if (!is_crazy($y) {<br>
        throw new InvalidArgumentException();<br>
    }<br>
    return $x - $y;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>str-&gt;ucfirst-&gt;strtolower<br>
</code></pre>
</td>
<td>
<pre><code>ucfirst(strtolower($str));<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>foo-&gt;bar(baz);<br>
</code></pre>
</td>
<td>
<pre><code>bar($foo, $baz);<br>
</code></pre>
</td>
</blockquote></tr>
</table></blockquote>

## Argument definition and validation ##
The recommended form of defining arguments for a function is to add annotations
with type, name, comments and validation above the function. The validation
can either be a single expression or an array. Inside validation expressions a
single underscore `_` can be used as a shortcut to the argument variable.

### Single expression argument validation ###
A single expression can be put on an indented line to validate the argument
passed to the function. If the expression is false an InvalidArgumentException
will be thrown.

Example:
```
@int myvariable_name
    !_->empty
fn foo
    pass
```

### Multiple expressions argument validation ###
A passed argument can be validated by multiple expressions by adding an array
on an indented line after the argument definition. The keys in the array are
the expressions to validate for trueness. The values can be one of
three things:

  * string: Will throw a an InvalidArgumentException with the string as the message.
  * Exception: Will throw the given exception.
  * return: Will return whats after `<-`.

If the key is omitted an InvalidArgumentException with an empty message will
be thrown.

Example:
```
@int myvariable_name
    [!_->empty: "Must not be empty."
     _ > 3: CustomException("Custom message.")
     _ != 3: <- ERROR_CODE
     _ !== 2 # Message will be empty.
    ]
fn foo
    pass
```

## Receiver syntax ##
A common paradigm in Snow is to use so-called `receiver functions`.

A receiver function is an expression followed by a single arrow `->` followed by
a function name, optionally followed by `(arg2, arg3)` as additional arguments.
The leading expression will be used as the first argument to the function.
Receiver functions can be chained like `myvar->myfunc1(arg2)->myfunc2` where
`myfunc1(arg2)` will serve as the first argument passed to `myfunc2`.

Unlike the tradition way of calling functions the trailing parenthesis must be
omitted for receiver functions calls without arguments.

### Examples ###
```
str = "Hello, World!\n"
str = str->strtolower       # Call the function "strtolower" with parameter 
                              "str" and assign the result to "str".
                                When there are no extra arguments, the 
                                parentheses must be omitted.
len = str->strlen           # Assign the length of "str" to "len".
len = str->strlen()         # ERROR. Trailing parenthesis must be omitted when 
                              the function does not have any arguments.
hello = str->substr(0 5)    # Assign the characters 0-5 of "str" to "hello".
```
The above examples can be written using the standard syntax as follows:
```
str = "Hello, World!\n"
str = strtolower(str)
len = strlen(str)
hello = substr(str 0 5)
```