#!/bin/bash
cd build; 
cd jacocoHtml; 
w3m -dump index.html 1>&2; 
cd edu.msoe.swe2721.lab10; 
w3m -dump Item.html 1>&2;
w3m -dump RefactoredItem.html 1>&2; 
