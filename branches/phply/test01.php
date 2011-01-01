<?php
function foo
    return 42;
    
function &bar ($a, $b=32, $c)
    $d = $b - 42;
    return $a * $b * $c * $d;
