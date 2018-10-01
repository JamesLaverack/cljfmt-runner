(ns cljfmt-runner.core-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [cljfmt-runner.core :as rcore]))

(def example-root "test-resources/example-project/")

(deftest io-test
  (testing "Test that a directory can be detected"
    (is (rcore/directory? (io/file example-root))))
  (testing "Test that a file can be detected"
    (is (rcore/file? (io/file (str example-root "deps.edn")))))
  (testing "Test that a Clojure source file can be detected"
    (is (rcore/clojure-source? (io/file (str example-root "src/example/hello.clj")))))
  (testing "Test that a ClojureScript source file can be detected"
    (is (rcore/clojure-source? (io/file (str example-root "src/example/hello.cljs")))))
  (testing "Test that an EDN source file can be detected"
    (is (rcore/clojure-source? (io/file (str example-root "deps.edn"))))))
