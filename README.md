# ZWareJavaWebClient
This project provides a Java implementation of Silicon Labs's Z-Ware Web APIs. It also provides a Sample Application in Java catering to Android platforms.Please check docs folder for some documentation. Please check silabs and zware websites for recent updates. 
https://www.silabs.com/wireless/z-wave
https://z-wavealliance.org/
https://github.com/OpenZWave
https://www.silabs.com/products/development-tools/software/z-wave/controller-sdk/z-ware

Note: This project is currently in very early prototype stage.

## TO DOs
Given that this is the first draft implementation, here are some TO DOs :
(not necessarily in the order of priority...)

* Standardize Java APIs and extend them to cover key Z-Ware APIs
* Need to add support for Add and Remove Node APIs.
* It currently needs a UZB connected to RPi3 with devices already added to the network.
* Add more features - Currently, version 1.0 only supports BINARY_SWITCH and upto 10 nodes and 10 end points.
* It is tested only with ZIPGW/Z-Ware running on RPi3 in http mode. https mode needs to be verified.
* Needs a good amount of refactoring:
* Refactor zw_web, ZwApiConstants,ZwDeviceStatus,etc. to a separate library say,  ZWareJavaWebClientLibrary.jar
* Refactor Views(Activities) to a separate ZWareJavaWebApp project and link with ZWareJavaWebClientLibrary.jar
* Define a Sample Application Framework for ZWareJavaWebClient
* ZWareJavaWebApp should be redesigned to use a simple UI framework supporting device discovery, device control and other common patterns
* The UI framework should be easily customizable to support any UI specification
* Release on Android Things platform with a sample application
* The binary release should include ZWareJavaWebClientLibrary.jar and ZWareJavaWebApp.apk in source form
* Look for To Do's, hacks and comments in the source code and address them.

