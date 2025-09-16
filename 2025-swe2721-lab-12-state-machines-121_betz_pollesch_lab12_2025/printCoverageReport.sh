#!/bin/bash
cd build; 
cd jacocoHtml; 
w3m -dump index.html 1>&2; 
cd SecurityLightController; 
w3m -dump LightControllerStateMachine.html 1>&2;
w3m -dump NewLightControllerStateMachine.html 1>&2;

