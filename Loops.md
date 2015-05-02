

## Introduction ##
Good old (t)rusty `foreach` has left the building, welcome the new and improved `for`.

`for` comes in two variants. The first is of the form `for v in a to b` or `for k v in arr` and compiles as `foreach`. The `a to b` part compiles to `range(a,b)`. The second is of the form `for i = a to b` or `for i = b downto a` and compiles to `for`. _Notice that this only works with numeric values._

A new feature compared to PHP is the before-and-after operator `%` that can be
used inside a loop to assign content to a variable before and after the loop.
## Matrix ##
<table cellpadding='5' border='1'>
<blockquote><tr>
<blockquote><td><strong>Snow</strong></td>
<td><strong>PHP</strong></td>
</blockquote></tr>
<tr>
<blockquote><td><strong>in</strong></td>
<td><strong>foreach</strong></td>
</blockquote></tr>
<tr>
<blockquote><td>for v in arr</td>
<td>foreach ($array as $v) {</td>
</blockquote></tr>
<tr>
<blockquote><td>for k v in arr</td>
<td>foreach ($array as $k => $v) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for v in 'a' to 'b'</td>
<td>foreach (range('a', 'b') as $v) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for k v in 1 to 10</td>
<td>foreach (range(1, 10) as $k => $v) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td><strong>=</strong></td>
<td><strong>for</strong>(numeric only)</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for i = 1 to 10</td>
<td>for ($i=1; $i<11; ++$i) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for i = 10 downto 1</td>
<td>for ($i=10; $i>0; --$i) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for i = a to somefunc()</td>
<td>for ($i=$a; $i<somefunc()+1; ++$i) {</td>
</blockquote></tr>
<tr>
<blockquote><td>for i = 'a' to 'b'</td>
<td>ERROR</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>for k v in array<br>
    text %= 'before' | "{k}: {v}" | 'after' <br>
</code></pre>
</td>
<td>
<pre><code>$text = 'before';<br>
foreach ($array as $k =&gt; $v) {<br>
    $text .= "$k: $v";<br>
}<br>
$text .= 'after';<br>
</code></pre>
</td>
</blockquote></tr>
</table></blockquote>

## Examples ##
We'll start with a basic example and work our way done to some more advanced stuff.
### 1) ###
Iterate over all the elements in a and print each value on a separate line.
```
a = [1 2 3]
for v in a
    echo v, "\n"
```
Output:
```
1
2
3
```

### 2) ###
Iterate over all the elements in a and prints each elements key and value on seperate lines.
```
a = [5:1 6:2 7:3]
for k v in a
    echo k, ": ", v, "\n
```
Output:
```
5: 1
6: 2
7: 3
```

### 3) ###
Factorial implementation.
```
fn factorial(int n)
    <- 1 when n == 0
    for i = n downto 2: 
        x *= n | i | null
    <- x
echo fn(5)
```
Output:
```
120
```

### 4) ###
Specifying character ranges
```
for char in 'a' to 'c'
    print char,"\n"
```
Output:
```
a
b
c
```