

## Introduction ##
Snow renames the return keyword to `<-` and introduces `<--` as an inner return
that works inside all control structures. The inner return allows for an control
structure to return a value which can either be stored to a variable or returned
from the function.

## Matrix ##
<table cellpadding='5' border='1'>
<blockquote><tr>
<blockquote><td><strong>Snow</strong></td>
<td><strong>PHP</strong></td>
</blockquote></tr>
<tr>
<blockquote><td><- some_var</td>
<td>return $some_var</td>
</blockquote></tr>
<tr>
<blockquote><td cellpadding='0' valign='top'>
<pre><code>fn double(int n): &lt;- n ∗ n<br>
</code></pre>
</td>
<td cellpadding='0' valign='top'>
<pre><code>function double($n) {<br>
    return $n ∗ $n;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td cellpadding='0' valign='top'>
<pre><code>a = if b &gt; 3<br>
    &lt;-- 42<br>
else<br>
    &lt;-- 666<br>
</code></pre>
</td>
<td cellpadding='0' valign='top'>
<pre><code>if ($b &gt; 3) {<br>
    $a = 42;<br>
} else {<br>
    $a = 666;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td cellpadding='0' valign='top'>
<pre><code>&lt;- if b &gt; 3<br>
    &lt;-- 42<br>
else<br>
    &lt;-- 666<br>
</code></pre>
</td>
<td cellpadding='0' valign='top'>
<pre><code>if ($b &gt; 3) {<br>
    return 42;<br>
} else {<br>
    return 666;<br>
}<br>
</code></pre>
</td>
</blockquote></tr>
<tr>
<blockquote><td cellpadding='0' valign='top'>
<pre><code>@array ints<br>
fn process_ints<br>
    return_code = for i in ints<br>
        if i-&gt;empty: &lt;-- HAS_EMPTY<br>
        if i &gt; 50: &lt;-- HAS_OUTOF_RANGE<br>
        new_ints[] = [] | i-&gt;do_stuff_to | null<br>
    &lt;- ['return_code': return_code ?? IS_OK, 'ints': new_ints]<br>
</code></pre>
</td>
<td cellpadding='0' valign='top'>
<pre><code>function process_ints(ints)<br>
    $new_ints = array();<br>
    foreach($ints as $i) {<br>
        if (empty($i)) {<br>
            $return_code = HAS_EMPTY;<br>
            break;<br>
        }<br>
        if ($i &gt; 50) {<br>
            $return_code = HAS_OUT_OF_RANGE;<br>
            break;<br>
        }<br>
        $new_ints[] = do_stuff_to($i);<br>
    }<br>
    return array(<br>
        'return_code' =&gt; isset($return_code) ? $return_code : IS_OK,<br>
        'ints' =&gt; $new_ints,<br>
    );<br>
</code></pre>
</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td cellpadding='0' valign='top'>
<pre><code>b = switch myvar<br>
    case "zoo"<br>
        &lt;-- "uber"<br>
    default<br>
        &lt;-- "nice"<br>
echo b<br>
</code></pre>
</td>
<td cellpadding='0' valign='top'>
<pre><code>switch ($myvar) {<br>
    case "zoo":<br>
        $b = "uber";<br>
        break;<br>
    default:<br>
        $b = "nice";<br>
        break;<br>
}<br>
echo $b;<br>
</code></pre>
</td>
</blockquote></tr>
</table>