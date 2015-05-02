

## Introduction ##
The apply operator `*` is a shortcut for the pattern:
```
# Normal syntax.
foo = foo->somefunc
# Apply operator syntax.
*foo->somefunc
```
So far it only works with receiver functions.

## Matrix ##
<table cellpadding='5' border='1'>
<blockquote><tr>
<blockquote><td><strong>Snow</strong></td>
<td><strong>PHP</strong></td>
</blockquote></tr>
<tr>
<blockquote><td><code>*</code>string->trim</td>
<td>$string = trim($string)</td>
</blockquote></tr>
</table></blockquote>

## Examples ##

### 1) ###
Introduction example one.
```
ar1 = ["el1" "el2"]
ar2 = ["el3" "el4"]
*ar1->array_merge(ar2)
```
Output:
```
# ar1 == ["el1" "el2" "el3" "el4"]
```