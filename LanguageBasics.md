

# Basics #

## Toggling Snow/HTML mode ##
All Snow files starts off in Snow mode. To escape to HTML mode you can use
`%>` and subsequently `<%` to enter Snow mode again.

Example:
```
echo 'This is Snow code, '
%> 
now we're talking HTML,
<%
echo ' and were back in Snow again.'
```

The details of using Snow as a template language are not yet defined.

## Instruction separation ##
In Snow, instructions are separated by a newline (\n, \r or \r\n depending on
system).

Valid syntax example:
```
echo "Hello, "
echo "World!\n"
```
Invalid syntax example:
```
echo "Hello, " echo "World!\n"
```

## Whitespace ##
Snow, as does python, operates with significant whitespace, meaning that
the code structure is managed by indenting/dedenting and not by using brackets
`{}`. The indentation size is fixed to 4 spaces and all lines must start with
a number of spaces to which 4 is a divisor. The only exceptions to this rule are
comment lines, inside multiline strings and inside brackets (`()`, `{}` and
`[]`).

Valid syntax example:
```
if a = 42
    # First comment line must start correctly
      but second and onwards can do what it want,
      etc.
    echo "a is 42"
else
    echo "a is wrong"
```
Invalid syntax example:
```
if a = 42
  # Comment.
   echo "a is 42"
else
   echo "a is wrong"
```

## Comma ##
The normal use of the comma as a separator in array definitions and function /
method calls and definitions should be omitted unless in cases where it
improves readability. Comma must be used or omitted consistently throughout the
entire construct.

Examples:
```
# Array definition.
a = [1 2 3]
a = [1,2, 3]
a = [
    'foo': 'bar',
    'baz': 'boz',
    [1:2, 3:4, [1 2 3]]
]
# Function call.
b = foo(1 bar() 3)
b = foo(1, bar(), 3)
# Function / Method definition.
fn foo (x y z)
fn foo (x, y, z)
# Multiple variable assignment.
a b = 3 4
a, b = 3, 4
```

Invalid example:
```
myvar = myfunc('foo' 3, true) # Comma not used or omitted consistently.
```

## Variables ##
Snow variables are defined without the prefixed `$` known and hated from PHP:
```
foo = 24
echo foo
```

Variable variables are defined by enclosing the variable in curly brackets:
```
fn myfunc: return 42
a = "myfunc"
echo {a}() # Echos '42'.
```

## Constants ##
Constants on the other hand are prefixed a dollar sign and can be assigned using
the "=" operator:
```
$I_AM_CONSTANT = 42
echo $I_AM_CONSTANT
```

## Strings ##
Snow introduces two new string types: triple double quoted strings `"""` and
triple single quoted strings `'''`. The use of heredoc and nowdoc strings is
still supported but not recommended.

A noteworthy change for all string types is that whitespace before the current
indentation level is stripped:

```
def foo
    <- """
    <div>
       It snows!
    </div> 
    """
```
Output:
```
<div>
    It snows!
</div>
```
Notice the removed whitespace.

### Quotation ###
Snow uses PHP's default quotation rules:
  * Double quotes - `"` and `"""` - have variable expansion.
  * Single quotes - `'` and `'''` - doesn't have variable expansion.
  * Heredoc - `<<<LABEL` and `<<<"LABEL"` - have variable expansion.
  * Nowdoc - `<<<'LABEL'` - doesn't have variable expansion.

Backslash (\) is the universal escape character.

Details can be viewed at: http://php.net/manual/en/language.types.string.php

### Variable expansion ###
Inside double quotes and heredoc various code enclosed in curly brackets will
add their value to the string. This is true for variables, constants, function
calls and method calls:
```
mystring = "I am {$A_CONSTANT} with {a_string} and {obj.method()} 
            and {obj::static_method()} and a {func_tion()}"
```

## Arrays ##
Arrays can be created only by defining keys and and values separated by a colon
inside square brackets `[]`. The use of comma between array elements is optional
but must stay the same for the entire array.

Example:
```
foo_arr = [1 2 3]
bar_arr = [
    'foo': 32,
    'bar': 52,
    boz: [1, 2, 3:123]
]
```

Invalid example:
```
baz_arr = [1,2 3] # Comma not used or omitted consistently.
```

## Multiple assignements ##
Snow supports multiple assignments as:
```
a b c = 1 2 3 # a = 1, b = 2 and c = 3.
arr_a['key'] arr_b['key'] c = ['yo'] ['uber'] get_c()
```

## Compilation target ##
Per default Snow will take the ".snow" file, change the extension to ".php" and
create/update a ".php" file in the same directory. Some frameworks however uses
other extensions than ".php" and to remedy this Snow has the `@compileto`
annotation. It should be placed in the beginning of the ".snow" file and tells
the Snow compiler to which file it should compile the PHP code. The location is
relative to the ".snow" file. Locations starting with "/" will have absolute
paths.

Example:
```
# In the file mymodule.snow.
@compileto mymodule.module
# More code here...

# In the file foo.snow.
@compileto /home/user/foo/foo.bar # Absolute path.
# More code here...
```