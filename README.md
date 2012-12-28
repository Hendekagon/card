# card

A Christmas card, which is also a demonstration of how to run Clojure code on either the desktop or
in a browser

## Usage

Generate Javascript code with:

lein cljsbuild auto

or

lein cljsbuild once

then open ./public/index.html

OR,

in a REPL,

(use '[card.plot.test])
(test-sierpinski)

This will bring up an Incanter plot of the card.

## License

Copyright © 2012 Matthew Chadwick

Distributed under the Eclipse Public License, the same as Clojure.
