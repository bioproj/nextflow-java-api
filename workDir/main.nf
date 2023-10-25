#!/usr/bin/env nextflow
nextflow.enable.dsl=2

process sayHello {
  input:
    val x
  output:
    stdout
  script:
    """
    echo '$x world!'

    """
}
println "111111111111111111111"
println params.fastq1
workflow {
  Channel.of('Bonjour', 'Ciao', 'Hello', 'Hola') | sayHello | view
}