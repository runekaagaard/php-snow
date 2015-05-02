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
<blockquote><td>for v k in arr</td>
<td>foreach ($array as $v =&gt;$k) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for v k in 'a' to 'b'</td>
<td>foreach (range('a', 'b') as $v =&gt; $k) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for v k in 1 to 10</td>
<td>foreach (range(1, 10) as $v =&gt; $k) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td><strong>=</strong></td>
<td><strong>for</strong>(numeric only)</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for i = 1 to 10</td>
<td>for ($i=1; $i&lt;11; ++$i) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for i = 10 downto 1</td>
<td>for ($i=10; $i&gt;0; --$i) {</td>
</blockquote></tr></blockquote>

<blockquote><tr>
<blockquote><td>for i = a to somefunc()</td>
<td>for ($i=$a; $i&lt;somefunc()+1; ++$i) {</td>
</blockquote></tr>
<tr>
<blockquote><td>for i = 'a' to 'b'</td>
<td>ERROR</td>
</blockquote></tr>
<tr>
<blockquote><td><strong>Using "putin"</strong></td>
<td></td>
</blockquote></tr>
<tr>
<td cellpadding='0' valign='top'><pre><code>for v k in arr putin a=''<br>
.= "%k: %v"</code></pre>
<blockquote></td>
<td cellpadding='0' valign='top'><pre><code>$a = '';<br>
foreach ($arr as $k =&amp;gt; $v) {<br>
$a .= "%k: %v";<br>
}</code></pre>
</td>
</blockquote></tr>
<tr>
<td cellpadding='0' valign='top'><pre><code>return for v k in arr putin a=''<br>
.= "%k: %v"</code></pre>
<blockquote></td>
<td cellpadding='0' valign='top'><pre><code>$a = '';<br>
foreach ($arr as $k =&amp;gt; $v) {<br>
$a .= "%k: %v";<br>
}<br>
return $a;</code></pre>
</td>
</blockquote></tr>
</table>