

## Introduction ##
Classes and objects works the same way as in PHP, but the syntax has been dried
up and made a lot faster to type and easier to look at.

Some things have new names:
  * The single arrow `->` is replaced by the comma '`.`'.
  * `$this->` is now just a single prefixing comma '`.`'.
  * `self::` is now just two prefixing colons `::`.
  * `public`, `protected`, `private`, `static` and `const` have been shortenedto `pub`, `pro`, `pri`, `sta` and `con`.

`con`, `pub`, `pro` and `pri` are no longer declared for each class variable but
are only used a maximum of one each with variables grouped below. The order of
them must be `con`, `pub`, `pro` and lastly `pri`. Other orderings will throw a
compile error.

Nested below `con`, `pub`, `pro` and `pri` keywords are an optional `sta` level.
Below that are groups of variable names optionally nested under similar types.
The types are written without the `@` like `str`, `flo`, etc.

Common for the grouping of `con`, `pub`, `pro`, `pri`, `sta`, variable type and
variable name is that when there is only one of a subgroup, the content below
must be moved to the same line.

Class variable declarations have access to constructor arguments, e.g. `myvar`
and each other, e.g. `.myvar`. Circular dependencies will throw a compile error.
They also have access to function calls.

The constructor `__construct()` method is removed and instead constructor code
is placed below the `pub`, `pro`, `pri` and `con` keywords and above the first
method. The arguments to the constructor are moved above the class definition
and have the same syntax as a normal function.

The destructor method `__destruct()` is unchanged, but should be the last
method declaration in the class. Any other location will throw a compile error.

The default `pub` keyword can not be used in front of methods but `pro`, `pri`,
and `sta` still can.

## Matrix ##
<table cellpadding='5' border='1'>
<blockquote><tr>
<blockquote><td><strong>Snow</strong></td>
<td><strong>PHP</strong></td>
</blockquote></tr></blockquote>

<a href='Hidden comment: Snow example'></a><br>
<tr><td cellpadding='0' valign='top'><pre><code><br>
.num = .min - 2</code></pre></td>
<a href='Hidden comment: PHP example'></a><br>
<td cellpadding='0' valign='top'><pre><code><br>
$this-&gt;num = $this-&gt;min - 2;</code></pre></td></tr>

<a href='Hidden comment: Snow example'></a><br>
<tr><td cellpadding='0' valign='top'><pre><code><br>
echo .myvar % ::my_static_var</code></pre></td>
<a href='Hidden comment: PHP example'></a><br>
<td cellpadding='0' valign='top'><pre><code><br>
echo $this-&gt;myvar . self::$my_static_var;</code></pre></td></tr>

<a href='Hidden comment: Snow example'></a><br>
<tr><td cellpadding='0' valign='top'><pre><code><br>
echo .myvar % ::$MYCONST</code></pre></td>
<a href='Hidden comment: PHP example'></a><br>
<td cellpadding='0' valign='top'><pre><code><br>
echo $this-&gt;myvar . self::MYCONST;</code></pre></td></tr>

<a href='Hidden comment: Snow example'></a><br>
<tr><td cellpadding='0' valign='top'>
<pre><code># Title about Foo<br>
    Description about the class Foo.<br>
@int x<br>
    # A positive number below 100.<br>
    0 &lt; _ &lt; 100<br>
@str s<br>
    # A positive number below 10.<br>
    0 &lt; _ &lt; 10<br>
class Foo extends Bar<br>
    con int $BAZ = 35<br>
        # The bazness.<br>
    pub<br>
        int <br>
            foo = 2 * x * ::$BAZ<br>
                # The fooness.<br>
            bar = .foo * 2<br>
                # The variable bar.<br>
        str s = s<br>
            # A string.<br>
    pro<br>
        int x = x<br>
        flo y = get_my_float(.x)<br>
    pri sta str privar = "Pure as Snow"<br>
<br>
    # The constructor.<br>
        Needs two args that bla.<br>
    parent::__construct(x s)<br>
<br>
    pri fn output: echo ::privar<br>
</code></pre>
</td>
<a href='Hidden comment: PHP example'></a><br>
<td cellpadding='0' valign='top'>
<pre><code>/**<br>
 * Title about Foo<br>
 *<br>
 * Description about the class Foo.<br>
 */<br>
class Foo extends Bar {<br>
    /**<br>
     * The bazness.<br>
     *<br>
     * @var int<br>
     */<br>
    const BAZ = 35;<br>
    /**<br>
     * The fooness.<br>
     *<br>
     * @var int<br>
     */<br>
    public $foo;<br>
    /**<br>
     * The variable bar.<br>
     *<br>
     * @var int<br>
     */<br>
    public $bar;<br>
    /**<br>
     * A string.<br>
     *<br>
     * @var string<br>
     */<br>
    public $s;<br>
    protected $x;<br>
    protected $y;<br>
    private static $privar = "Pure as Snow";<br>
<br>
    /**<br>
     * The constructor.<br>
     * <br>
     * Needs two args that bla.<br>
     *<br>
     * @param int $x<br>
     *   A positive number below 100.<br>
     * @param int $s<br>
     *   A positive number below 10.<br>
     */<br>
    public function __construct($x, $s) {<br>
        if (!(0 &lt; $x &amp;&amp; $x &lt; 100)) {<br>
            throw new InvalidArgumentException;<br>
        }<br>
        if (!(0 &lt; $s &amp;&amp; $s &lt; 10)) {<br>
            throw new InvalidArgumentException;<br>
        }<br>
        $this-&gt;x = $x;<br>
        $this-&gt;s = $s;<br>
        $this-&gt;foo = 2 * $x * self::BAZ;<br>
        $this-&gt;bar = $this-&gt;foo * 2;<br>
        $this-&gt;y = get_my_float($this-&gt;x);<br>
        parent::__construct($x, $s);<br>
    }<br>
<br>
    private function output() {<br>
        echo self::$privar;<br>
    }<br>
}<br>
</code></pre>
</td></tr>
<blockquote><tr>
<blockquote><td><strong>Invalid syntax</strong></td>
</blockquote></tr></blockquote>

<a href='Hidden comment: Snow example'></a><br>
<tr><td cellpadding='0' valign='top'>
<pre><code>class Foo<br>
    pri<br>
        int<br>
            bar<br>
            baz<br>
    pub<br>
        str boz<br>
    <br>
</code></pre>
</td>
<a href='Hidden comment: PHP example'></a><br>
<td cellpadding='0' valign='top'>
The order must be <code>con</code>, <code>pub</code>, <code>pro</code> and <code>pri</code>.<br>
</td></tr>

<tr><td cellpadding='0' valign='top'>
<pre><code>class Foo<br>
    pri<br>
        int foo<br>
        int bar<br>
</code></pre>
</td>
<a href='Hidden comment: PHP example'></a><br>
<td cellpadding='0' valign='top'>
More than one of the same type must be nested.<br>
</td></tr>

<tr><td cellpadding='0' valign='top'>
<pre><code>class Foo<br>
    pri<br>
        int <br>
            foo<br>
</code></pre>
</td>
<a href='Hidden comment: PHP example'></a><br>
<td cellpadding='0' valign='top'>
When there is only one type it must be collapsed.<br>
</td></tr>

<a href='Hidden comment: Snow example'></a><br>
<tr><td cellpadding='0' valign='top'>
<pre><code>class Foo<br>
    pub<br>
        str<br>
            a = .b % "abc"<br>
            b = .a % "def"<br>
</code></pre>
</td>
<a href='Hidden comment: PHP example'></a><br>
<td cellpadding='0' valign='top'>
Cyclical error. <code>a</code> and <code>b</code> depends on each other.<br>
</td></tr>
</table>

## Examples ##
Below is an example of a FTP class.
```
# Class handling FTP operations.
    Wrapper on top of FTP protocol that enables file/directory listing,
    upload/download, etc.
@str host
    # The ftp host to connect to.
    !_->empty
@int port=21
    # The port to connect to.
    0 < _ < 65536
@int timeout=30
    # Connect timeout, bail out if exceeded. 0 means wait forever.
    0 <= _
class Ftp
    con int
        OPT_TRANS_ASCII = FTP_ASCII
        OPT_TRANS_BINARY = FTP_BINARY
        OPT_TRANS_AUTO = 3
    pro
        arr
            ascii_types = ["text" "csv"]
            binary_types = ["jpg" "jpeg" "gif" "psd"]
        int default_transmode = ::OPT_TRANS_ASCII
        res conn

    # Constructor.
        Tries to connect to server or throws an exception.
    .conn = ftp_connect(host port timeout) ?! throw Exception(
        "Couldn't connect to host '{host}'")

    @str user
    @str pass
    fn login: <- ftp_login(.conn user pass)

    @str local
    @str remote
    @int trans_mode = ::OPT_TRANS_AUTO
    fn put
        trans_mode = get_trans_mode(local) when trans_mode == ::OPT_TRANS_AUTO
        ftp_put(.conn local remote trans_mode) ?! throw Exception(
            "Couldn't get file '{remote}'")

    @str local
    @str remote
    @int trans_mode = ::OPT_TRANS_AUTO
    fn get
        trans_mode = get_trans_mode(local) when trans_mode == ::OPT_TRANS_AUTO
        ftp_get(.conn local remote trans_mode) ?! throw Exception(
            "Couldn't get file '{remote}'")

    # Checks whether a file exists on the remote side.
    @str file
        # The filename to check for.
        !_->empty
    fn file_exists
        <- .conn->ftp_nlist(file->dirname)->in_array(file->basename)

    # Determine transfermode to use.
    @str file
    fn get_trans_mode
        for ext in .ascii_types
            <- ::OPT_TRANS_ASCII when file->substr(-ext->strlen) == ext
        for ext in .binary_types
            <- ::OPT_TRANS_BINARY when file->substr(-ext->strlen) == ext
        <- default_transmode

    fn close: <- ftp_close(.conn)

try
    ftp = Ftp("my.host")
    ftp.login("user" "pass")
    ftp.get("/remote/file" "/local/file")
catch Exception e
    e->var_dump
```