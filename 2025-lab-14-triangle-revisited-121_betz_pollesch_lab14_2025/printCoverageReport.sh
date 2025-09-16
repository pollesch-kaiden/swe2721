#!/bin/bash
cd build; 
cd jacocoHtml; 
w3m -dump index.html 1>&2; 
cd edu.msoe.swe2721.lab14; 
w3m -dump NumericParser.html 1>&2;
w3m -dump Triangle.html 1>&2; 
w3m -dump TriangleFactory.html 1>&2; 
