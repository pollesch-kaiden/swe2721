#!/bin/bash
cd build; 
cd jacocoHtml; 
w3m -dump index.html 1>&2; 
cd edu.msoe.swe2721.lab11; 
w3m -dump StockQuoteAnalyzer.html 1>&2;

