

This page is a stub.

## Introduction ##

The reasoning behind the before-after is to try to improve the following
pattern:
```
@array arr
def somefunc
    rows = [get_one(), get_other()]
    for line in arr
        rows[] = 42
        rows[] = line
    rows[] = get_third()
    rows[] = get_fourth()
    <- rows
```

Using the before-after operator that would read:
```
@array arr
def somefunc
    for line in arr
        rows []= <- get_one() get_other() | 42 line | get_third() get_fourth()
```

## Matrix ##
<table cellpadding='5' border='1'>
<blockquote><tr>
<blockquote><td><strong>Snow</strong></td>
<td><strong>PHP</strong></td>
</blockquote></tr>
<tr>
<blockquote><td><strong>Second headline</strong></td>
<td><strong>Second headline</strong></td>
</blockquote></tr>
<tr>
<blockquote><td>Single line</td>
<td>Single line</td>
</blockquote></tr>
<tr>
<blockquote><td>
<pre><code>Multi-<br>
line<br>
</code></pre>
</td>
<td>
<pre><code>Multi-<br>
line<br>
</code></pre>
</td>
</blockquote></tr>
</table>