# cljfmt-runner

[![CircleCI](https://circleci.com/gh/JamesLaverack/cljfmt-runner.svg?style=svg)](https://circleci.com/gh/JamesLaverack/cljfmt-runner) [![No Maintenance Intended](http://unmaintained.tech/badge.svg)](http://unmaintained.tech/)

[Cljfmt](https://github.com/weavejester/cljfmt) runner for [native Clojure projects](https://clojure.org/reference/deps_and_cli).

**This project is unmantained and archived. If you still need this functionality, please refer to one of the many fabulious [forks](https://github.com/JamesLaverack/cljfmt-runner/network/members).**

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
                       :sha "97960e9a6464935534b5a6bab529e063d0027128"}}
         :main-opts ["-m" "cljfmt-runner.check"]}
  :lint/fix {:main-opts ["-m" "cljfmt-runner.fix"]}
```

You can then run `check` with `clojure -A:lint` and `lint/fix` with `clojure -A:lint:lint/fix`.

You can, of course, name these aliases whatever you want.

The following one liner also works:

```clj
clojure -Sdeps "{:deps 
                   {com.jameslaverack/cljfmt-runner
                      {:git/url \"https://github.com/JamesLaverack/cljfmt-runner\"
                       :sha \"97960e9a6464935534b5a6bab529e063d0027128\"}}}" -m cljfmt-runner.check
```

It's advisable to find the most recent sha from this repo for latest features.

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

