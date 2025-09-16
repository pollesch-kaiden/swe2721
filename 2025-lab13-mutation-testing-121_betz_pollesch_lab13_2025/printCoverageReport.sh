#!/bin/bash
cd build; 
cd jacocoHtml; 
w3m -dump index.html 1>&2; 
cd transcriptAnalyzer; 
w3m -dump  Transcript.html 1>&2;
w3m -dump CompletedCourse.html 1>&2;
w3m -dump AcademicQuarter.html 1>&2;


