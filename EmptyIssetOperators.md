

## Introduction ##
A common pattern in PHP is checking if a variable or the return of a function is set or is empty and then performing assignments or changing the control flow. The postfix operators `??` and `?!` provides shortcuts for this. `??` and `?!` resembles `isset()` and `!empty()` respectively.

Notice that if you use `??` or `?!` _flow control_ on function calls the Snow compiler will introduce a magic variable named `status` because `isset()` and `empty()` only works on variables. If a variable named `status` already exists in the same scope a compile error will be thrown.


## Matrix ##
<table cellpadding='5' border='1'>
<blockquote><tr>
<blockquote><td><strong>Snow</strong></td>
<td><strong>PHP</strong></td>
</blockquote></tr>
<tr>
<blockquote><td><strong>Assignment</strong></td>
<td></td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>a = b ?? 42<br>
</code></pre>
</td>
<td>
<pre><code>$a = isset($b) ? $b : 42;<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>a = b ?! 42<br>
</code></pre>
</td>
<td>
<pre><code>$a = !empty($b) ? $b : 42;<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>a = get_stuff('foo') ?? 42<br>
</code></pre>
</td>
<td>
<pre><code>$a = get_stuff('foo');<br>
if (!isset($a)) {<br>
    $a = 42;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<td>
<pre><code>a = get_stuff('foo') ?! 42<br>
</code></pre>
<blockquote></td>
<td>
<pre><code>$a = get_stuff('foo');<br>
if (empty($a)) {<br>
    $a = 42;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td><strong>Flow control</strong></td>
<td></td>
</blockquote></tr>
<tr>
<td>
<pre><code>b ?!: throw InvalidArgumentException<br>
</code></pre>
<blockquote></td>
<td>
<pre><code>if (empty($b)) {<br>
    throw new InvalidArgumentException;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<td>
<pre><code>authors['fans'] ??<br>
    for fan in get_fans()<br>
        authors['fans'] %= '&lt;ul&gt;' | "&lt;li&gt;{fan}&lt;/li&gt;" | '&lt;/ul&gt;'<br>
</code></pre>
<blockquote></td>
<td>
<pre><code>if (!isset($authors['fans'])) {<br>
    $authors['fans'] = '&lt;ul&gt;';<br>
    foreach (get_fans() as $fan) {<br>
        $authors['fans'] .= "&lt;li&gt;$fan&lt;/li&gt;";<br>
    }<br>
    $authors['fans'] .= '&lt;/ul&gt;';<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<td>
<pre><code>get_stuff('foo') ?!: throw MyException<br>
</code></pre>
<blockquote></td>
<td>
<pre><code>$status = get_stuff('foo');<br>
if (empty($status)) {<br>
    throw new MyException;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<td>
<pre><code>status = 42<br>
get_stuff('foo') ?!: throw MyException<br>
</code></pre>
<blockquote></td>
<td>Compile error, magic variable "status" already exists in scope.<br>
</td>
</blockquote></tr>
</table>