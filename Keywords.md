

## Introduction ##
All Snow keywords are listed below. Of interest maybe also be the list of changed [Operators](Operators.md).

## Deleted Keywords ##
| **PHP**         | **Snow**       | **Comment** |
|:----------------|:---------------|:------------|
| as            | _deleted_    | See `in`. |
| foreach       | _deleted_    |  |
| var           | _deleted_    |  |

## Changed Keywords ##
| **PHP**         | **Snow**       | **Comment** |
|:----------------|:---------------|:------------|
| elseif        | elif         | `else if` is disabled. |
| enddeclare    | end          |  |
| endfor        | end          |  |
| endforeach    | end          |  |
| endif         | end          |  |
| endswitch     | end          |  |
| endwhile      | end          |  |
| array()       | `[]`         |  |
| instanceof    | isa          |  |
| function      | fn           |  |
| continue      | next         |  |

## Unchanged Keywords ##
| **PHP**         | **Snow**       | **Comment** |
|:----------------|:---------------|:------------|
| abstract      | _unchanged_  |  |
| and           | _unchanged_  |  |
| break         | _unchanged_  |  |
| case          | _unchanged_  |  |
| catch         | _unchanged_  |  |
| class         | _unchanged_  |  |
| clone         | _unchanged_  |  |
| declare       | _unchanged_  |  |
| default       | _unchanged_  |  |
| do            | _unchanged_  |  |
| else          | _unchanged_  |  |
| extends       | _unchanged_  |  |
| final         | _unchanged_  |  |
| for           | _unchanged_  |  |
| if            | _unchanged_  |  |
| implements    | _unchanged_  |  |
| interface     | _unchanged_  |  |
| namespace     | _unchanged_  |  |
| new           | _unchanged_  |  |
| or            | _unchanged_  |  |
| switch        | _unchanged_  |  |
| throw         | _unchanged_  |  |
| try           | _unchanged_  |  |
| use           | _unchanged_  |  |
| while         | _unchanged_  |  |
| xor           | _unchanged_  |  |

## Shortened Access Modifier Keywords ##
| **PHP**         | **Snow**       | **Comment** |
|:----------------|:---------------|:------------|
| global        | glo          |  |
| private       | pri          |  |
| protected     | pro          |  |
| public        | pub          |  |
| static        | sta          |  |
| const         | con          |  |

## New keywords ##
| **Snow**       | **Comment** |
|:---------------|:------------|
| when         | Postfix `if` sentence. |
| fallthru     | Disable default `break` in `case` sentences. |
| in           | Used with the new `for` loop. |
| to           | Used with the new `for` loop. `for i = 1 to 5` |
| downto       | Used with the new `for` loop. `for i = 5 downto 1` |
| <--          | Inner return, used inside control structures. |
| ??           | isset operator |
| ?!           | empty operator |
| `|`          | Before-and-after operator |

## Language constructs ##
| **PHP**          | **Snow**       | **Comment** |
|:-----------------|:---------------|:------------|
| die()          | _deleted_    | Use `exit` instead. |
| echo()         | _unchanged_  |  |
| empty()        | _unchanged_  |  |
| exit()         | _unchanged_  |  |
| eval()         | _deleted_    | Use `<?php ?>` injection instead. |
| include()      | _unchanged_  |  |
| include\_once() | _unchanged_  |  |
| isset()        | _unchanged_  |  |
| list()         | _unchanged_  |  |
| require()      | _unchanged_  |  |
| require\_once() | _unchanged_  |  |
| return         | <-           |  |
| print()        | _deleted_    | Use `echo` instead. |
| unset()        | _unchanged_  |  |

## Compile-time constants ##
| **PHP**           | **Snow**      | **Comment** |
|:------------------|:--------------|:------------|
| `__CLASS__`     | _unchanged_ |  |
| `__DIR__`       | _unchanged_ |  |
| `__FILE__`      | _unchanged_ |  |
| `__FUNCTION__`  | _unchanged_ |  |
| `__METHOD__`    | _unchanged_ |  |
| `__NAMESPACE__` | _unchanged_ |  |

## Standard Defined Classes ##
| **PHP**                    | **Snow**               | **Comment** |
|:---------------------------|:-----------------------|:------------|
| Directory                | _unchanged_          |  |
| stdClass                 | _unchanged_          |  |
| exception                | _unchanged_          |  |
| php\_user\_filter          | _unchanged_          |  |
| Closure                  | _unchanged_          |  |
| `__PHP_Incomplete_Class` | _unchanged_          |  |

## Identifiers used inside a class ##
| **PHP**  | **Snow**      | **Comment** |
|:---------|:--------------|:------------|
| $this-> | . | `$this` is removed for brevity. |
| self:: | :: | `self ` is removed for brevity. |
| parent | _unchanged_ |  |
| `__callStatic` | _unchanged_ |  |
| `__toString` | _unchanged_ |  |

## References ##
  * http://www.php.net/manual/en/reserved.keywords.php
  * http://www.php.net/manual/en/reserved.classes.php