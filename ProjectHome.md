**2012-04-17, news:** The snowball is rolling again. The code and documentation has moved to https://github.com/runekaagaard/snowscript.

Snow is a little language that compiles to PHP. It has its own syntax inspired by Python, Ruby, Go and Scala that strives to be DRY, clean and easy to read as well as write. For those familiar with
[Coffescript](http://jashkenas.github.com/coffee-script/) you could say that
Snow is to PHP what Coffeescript is to Javascript.

Snow is called "Snow" because it should look "pure as snow", because is has significant WHITEspace and because the winter of 2010 in Copenhagen was extremely cold and snowy.

Snow is still in its _implementation phase_. Its main features are:
  * Is infinitely more dry than PHP.
  * Kills the dreaded `$`, `$this` and `self`.
  * Has a _lot_ less characters (about 1:1.7) than PHP and _much_ fewer lines (about 1:2).
  * Has chainable receiver functions.
  * Can contain advanced expressions in class attribute definitions.
  * Removes boring repetitive code patterns.
  * Does not try to have a Java like syntax.
  * Has significant whitespace.
  * Can coexist naturally with existing non-Snow PHP code.
  * Generates idiomatic, high quality looking PHP.
  * Can compile existing PHP to snow.
  * Is very well tested with PHPT.
  * Makes PHP fun again.

## Example ##
Words, words, words, now _show me the code!_ Well certainly. Here is a short example (or at least the Snow version is short:).

<table cellpadding='5' border='1'>
<blockquote><tr>
<blockquote><td><strong>Snow</strong></td>
<td><strong>PHP</strong></td>
</blockquote></tr>
<tr><td cellpadding='0' valign='top'>
<pre><code>@str title=null
    # The title shown above the table.
class SumTable
    # Renders a html table of numbers.
        A sum column is added to each row and the total sum 
        of all cells is shown on top.    
    pri 
        title_default = t('Untitled')
        title = title ?! .title_default
        rows = []
        sum = 0
    
    @arr row
        # List of integers.
        [!_-&gt;empty: "$row cannot be empty."]
    fn add_row(row)
        # Append a table row.
        n = row-&gt;count
        for col in row
            row[n] += 0 | col | null
        .sum += row[n]
        .rows[] = row

    fn render
        # Returns table as html.
        for row in .rows
            html %= "&lt;table&gt;" | .render_row(row) | "&lt;/table&gt;"
        &lt;- "&lt;h2&gt;{.title}: {.sum}&lt;/h2&gt;" % html
            
    pri fn render_row(row)
        for col in row
            html %= "&lt;tr&gt;" | "&lt;td&gt;{col}&lt;/td&gt;" | "&lt;/tr&gt;"
        &lt;- html
</code></pre>
</td>
<td cellpadding='0' valign='top'>
<pre><code>/**
 * Renders a html table of numbers.
 *       
 * A sum column is added to each row and the total sum of 
 * all cells is shown on top.
 */
class SumTable {
    private $title_default;
    private $title;
    private $rows = array();
    private $sum = 0;

    /**
     * Constructor.
     *
     * @param string $title
     *   The title shown above the table.
     */
    public function __construct($title=null) {
        $this-&gt;title_default = t('Untitled');
        $this-&gt;title = !empty($title) 
            ? $title 
            : $this-&gt;title_default;
    }

    /**
     * Append a table row.
     *
     * @param array $row
     *   List of integers.
     */
    public function add_row($row) {
        if (empty($row)) {
            throw new InvalidArgumentException(
                '$row cannot be empty');
        }
        $n = count($row);
        $row[$n] = 0;
        foreach ($row as $col) {
            $row[$n] += $col;
        }
        $this-&gt;sum += $row[$n];
        $this-&gt;rows[] = $row;
    }

    /**
     * Returns table as html.
     */
    public function render() {
        $html = '&lt;table&gt;';
        foreach ($this-&gt;rows as $row) {
            $html .= $this-&gt;render_row($row);
        }
        $html .= '&lt;/table&gt;';
        return "&lt;h2&gt;$this-&gt;title: $this-&gt;sum&lt;/h2&gt;" . $html;
    }

    private function render_row($row) {
        $html = '&lt;tr&gt;';
        foreach ($row as $col) {
            $html .= "&lt;td&gt;$col&lt;/td&gt;";
        }
        $html .= '&lt;/tr&gt;';
        return $html;
    }
}
</code></pre>
</td></tr></table></blockquote>

## Continue reading ##
There is a lot more cool stuff going on so check out the [documentation](TOC.md).

## Current status ##
The specification and accompanied documentation is pretty completed and there is a somewhat working lexer built in PLY. See below example. The yellow lines are Snow, the other are the lexed tokens.

![http://i.imgur.com/pE0RC.png](http://i.imgur.com/pE0RC.png)

It's soon time to start working on the parser and then the PHP generator.