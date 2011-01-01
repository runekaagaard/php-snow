from simpleparse.parser import Parser

# Example code.
code = '''\
const myconst = not_implemented;
use MySpace;
use MySpace\YourSpace;
use MySpace\YourSpace as ThySpace;
if (not_implemented) {
    not_implemented
}
__halt_compiler();
'''

# Parse code.
parser = Parser(open('php.ebnf').read())
success, result_trees, next_char = parser.parse(code)

def print_tree(rs, code, indent=0):
    """
    Pretty prints the tree.
    """
    for r in rs:
        if r[0] == 'ws': continue
        if r[0] == 'string':
            value = ' = %s' % code[r[1]:r[2]]
        else:
            value = ''
        print " " * 4 * indent + r[0] + value
        if r[3]:
            print_tree(r[3], code, indent+1)
print_tree(result_trees, code)
