# cljfmt-runner

[![CircleCI](https://circleci.com/gh/JamesLaverack/cljfmt-runner.svg?style=svg)](https://circleci.com/gh/JamesLaverack/cljfmt-runner)

[Cljfmt](https://github.com/weavejester/cljfmt) runner for [native Clojure projects](https://clojure.org/reference/deps_and_cli).

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
                       :sha "51f85c9d6cc67107620749ceacd46120647fe107"}}
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
                       :sha \"57e956856669efee854e00765ad00e1eebaace2a\"}}}" -m cljfmt-runner.check
```

It's advisable to find the most recent sha from this repo for latest features.

## Configuration

If you want to automatically scan directories in addition to `src` and `test`, then you can specify them in a `cljfmt.edn` file at the top level of your project directory.

``` edn
{:dirs ["extra-dir"]}
```

Alternatively, you can specify extra directories on the command line with the `-d` flag.

For example, using the alias setup from the example above, if you wanted to scan a `dev` directory for Clojure sources too then you could do this with `clj -A:lint -d dev`.

## Building a Native Image/Executable

cljfmt is a great tool, but it’s historically been not a great fit for situations wherein latency is crucial, such as in a [git pre-commit hook](https://git-scm.com/book/en/v2/Customizing-Git-Git-Hooks). This is [probably](https://clojureverse.org/t/tricks-to-make-clojure-startup-time-faster/1176/13?u=avi) because it and its libraries are not currently [AOT-compiled](https://en.wikipedia.org/wiki/Ahead-of-time_compilation).

To address this, this project currently supports compilation to a native image/executable via [GraalVM](https://www.graalvm.org/) and [clj.native-image](https://github.com/taylorwood/clj.native-image). (Currently only the `check` entrypoint works; there are some outstanding issues with compiling the `fix` entrypoint. This isn’t ideal but the `check` entrypoint should be sufficient for use cases such as git pre-commit hooks.)

You can make your own native executable like so:

1. Clone this project
1. Download GraalVM and extract it somewhere on your system
1. Run this in your terminal:
   ```shell
   export GRAALVM_HOME=/path/to/the/home/dir/within/the/graalvm/dir
   cd /path/to/wherever/you/cloned/this/project
   clojure -A:native-image-check
   ```

If that worked, you should see in the current dir a new file: `cljfmt-check`. Move that file to wherever you wish, and it should get the job done.

(As a suggestion, some of us do this: `mv cljfmt-check ~/bin/cljfmt` — the shorter name is just more convenient.)

## License

Distributed under the Eclipse Public License either version 2.0 or (at your option) any later version.

Copyright © 2018 James Laverack.

Portions of this code (in particular `src/cljfmt_runner/diff.clj`) are © 2016 James Reeves and taken from https://github.com/weavejester/cljfmt
