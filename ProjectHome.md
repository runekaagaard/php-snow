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
<pre><code>@str title=null<br>
    # The title shown above the table.<br>
class SumTable<br>
    # Renders a html table of numbers.<br>
        A sum column is added to each row and the total sum <br>
        of all cells is shown on top.    <br>
    pri <br>
        title_default = t('Untitled')<br>
        title = title ?! .title_default<br>
        rows = []<br>
        sum = 0<br>
    <br>
    @arr row<br>
        # List of integers.<br>
        [!_-&gt;empty: "$row cannot be empty."]<br>
    fn add_row(row)<br>
        # Append a table row.<br>
        n = row-&gt;count<br>
        for col in row<br>
            row[n] += 0 | col | null<br>
        .sum += row[n]<br>
        .rows[] = row<br>
<br>
    fn render<br>
        # Returns table as html.<br>
        for row in .rows<br>
            html %= "&lt;table&gt;" | .render_row(row) | "&lt;/table&gt;"<br>
        &lt;- "&lt;h2&gt;{.title}: {.sum}&lt;/h2&gt;" % html<br>
            <br>
    pri fn render_row(row)<br>
        for col in row<br>
            html %= "&lt;tr&gt;" | "&lt;td&gt;{col}&lt;/td&gt;" | "&lt;/tr&gt;"<br>
        &lt;- html<br>
</code></pre>
</td>
<td cellpadding='0' valign='top'>
<pre><code>/**<br>
 * Renders a html table of numbers.<br>
 *       <br>
 * A sum column is added to each row and the total sum of <br>
 * all cells is shown on top.<br>
 */<br>
class SumTable {<br>
    private $title_default;<br>
    private $title;<br>
    private $rows = array();<br>
    private $sum = 0;<br>
<br>
    /**<br>
     * Constructor.<br>
     *<br>
     * @param string $title<br>
     *   The title shown above the table.<br>
     */<br>
    public function __construct($title=null) {<br>
        $this-&gt;title_default = t('Untitled');<br>
        $this-&gt;title = !empty($title) <br>
            ? $title <br>
            : $this-&gt;title_default;<br>
    }<br>
<br>
    /**<br>
     * Append a table row.<br>
     *<br>
     * @param array $row<br>
     *   List of integers.<br>
     */<br>
    public function add_row($row) {<br>
        if (empty($row)) {<br>
            throw new InvalidArgumentException(<br>
                '$row cannot be empty');<br>
        }<br>
        $n = count($row);<br>
        $row[$n] = 0;<br>
        foreach ($row as $col) {<br>
            $row[$n] += $col;<br>
        }<br>
        $this-&gt;sum += $row[$n];<br>
        $this-&gt;rows[] = $row;<br>
    }<br>
<br>
    /**<br>
     * Returns table as html.<br>
     */<br>
    public function render() {<br>
        $html = '&lt;table&gt;';<br>
        foreach ($this-&gt;rows as $row) {<br>
            $html .= $this-&gt;render_row($row);<br>
        }<br>
        $html .= '&lt;/table&gt;';<br>
        return "&lt;h2&gt;$this-&gt;title: $this-&gt;sum&lt;/h2&gt;" . $html;<br>
    }<br>
<br>
    private function render_row($row) {<br>
        $html = '&lt;tr&gt;';<br>
        foreach ($row as $col) {<br>
            $html .= "&lt;td&gt;$col&lt;/td&gt;";<br>
        }<br>
        $html .= '&lt;/tr&gt;';<br>
        return $html;<br>
    }<br>
}<br>
</code></pre>
</td></tr></table></blockquote>

## Continue reading ##
There is a lot more cool stuff going on so check out the [documentation](TOC.md).

## Current status ##
The specification and accompanied documentation is pretty completed and there is a somewhat working lexer built in PLY. See below example. The yellow lines are Snow, the other are the lexed tokens.

![http://i.imgur.com/pE0RC.png](http://i.imgur.com/pE0RC.png)

It's soon time to start working on the parser and then the PHP generator.