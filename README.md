# cljfmt-runner

[![CircleCI](https://circleci.com/gh/JamesLaverack/cljfmt-runner.svg?style=svg)](https://circleci.com/gh/JamesLaverack/cljfmt-runner)

Cljfmt runner for native Clojure projects.

## Operation

The runner detects any Clojure or Clojurescript files located in the `src` and `test` directories when run.

It can be run in two modes:

* `check` will check all source files and will return a diff of any proposed changes. It also returns a nonzero exit code on any incorrectly formatted files and so is suitable for use in a continuous integration build to enforce code style correctness.
* `fix` will fix any problems it sees in source files and write the updated changes to disk.

## Installation

Recommended approach is to use an alias in your `deps.edn` file, bring in this project as a dependency, and set the main class to the operation you want to perform.

### Example

Within an `:alias` block:

``` edn
  :lint {:extra-deps {com.jameslaverack/cljfmt-runner
                      {:git/url "https://github.com/JamesLaverack/cljfmt-runner"
                       :sha "221bb341901ea8754f40139939b318ed7344d7ed"}}
         :main-opts ["-m" "cljfmt-runner.check"]}
  :lint/fix {:main-opts ["-m" "cljfmt-runner.fix"]}
```

You can then run `check` with `clj -A:lint` and `fix` with `clj -A:lint:lint/fix`. You can, of course, name these aliases whatever you want.

## Configuration

If you want to automatically scan directories in addition to `src` and `test`, then you can specify them in a `cljfmt.edn` file at the top level of your project directory.

``` edn
{:dirs ["extra-dir"]}
```

Alternatively, you can specify extra directories on the command line with the `-d` flag.

For example, using the alias setup from the example above, if you wanted to scan a `dev` directory for Clojure sources too then you could do this with `clj -A:lint -d dev`.

## License

Distributed under the Eclipse Public License either version 2.0 or (at your option) any later version.

Copyright © 2018 James Laverack.

Portions of this code (in particular `src/cljfmt_runner/diff.clj`) are © 2016 James Reeves and taken from https://github.com/weavejester/cljfmt

