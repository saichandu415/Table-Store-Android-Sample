![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=for-the-badge)
# Table-Store-Android-Sample
This is the sample project created to demonstrate Table Store capabilities of the Alibaba Store

## Built using

 - Android Studio 3.0.0
 - SLF4J 1.7.7
 - Table Store SDK 4.2.0

## Overview of this application

This application shows a demo of "Table Store" service capability of the Alibaba Cloud. Please find the device screenshots in "Screenshots" folder.

## Prerequisites

 - You need a Alibaba Cloud Account. If you want one you can get one with **free credit of $300** by registering [here](https://account-intl.aliyun.com/register/intl_register.htm?biz_params=%7B%22intl%22%3A%22%7B%5C%22referralCode%5C%22%3A%5C%226qnq3f%5C%22%7D%22%7D)

## Installation

 1. Clone or download the project into your Android Studio 3.0.0 
 2. You need the /libs folder for proper functioning. **DON'T REMOVE LIBS FOLDER.**

### Access Keys Information
Please replace your information from your Alibaba Cloud console in "strings.xml".

```
<resources>
    <string name="app_name">YOUR APP NAME</string>

    <!-- Alibaba Table Store Service details-->
    <!-- Please replace this details with your own-->
    <!--Public Endpoint-->
    <string name="Endpoint">UPDATE YOUR ENDPOINT FOR QUEUES HERE</string>
    <!-- Access ID -->
    <string name="AccessKey">UPDATE YOUR ACCESS ID</string>
    <!-- Access key Secret -->
    <string name="AccessKeySecret">UPDATE YOUR ACCESS KEY HERE</string>
    <!-- Queue Names -->
    <string name="InstanceName">AndroidDemo</string>
    <!--App Related Strings-->
    <string name="NameHint">Name</string>
</resources>
```

## Bugs & Feedback
For bugs, questions and discussions please use the [Github Issues.](https://github.com/saichandu415/Table-Store-Android-Sample/issues)


## Snapshots
|                                        Home Screen                                         |                                               Fetching Values on Load                                               |                                         Before User Form                                         |
|:-------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/Home_Screen.png" width="216" height="384" /> | <img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/Fetching_values_onLoad.png" width="216" height="384" /> | <img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/UserForm_before.png" width="216" height="384" /> |

|                                        Remote Config Before                               |                                               Values Entered                                               |                                         Values Updating                                         |
|:-------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/Remote_Config_Before.png" width="216" height="384" /> | <img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/Remote_Config_Values_Entered.png" width="216" height="384" /> | <img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/Remote_Config_Updating_Values.png" width="216" height="384" /> |

|                                        Remote Config values udpated                             |                                               User Form age values before Update                                               |                                         User form values after config Update                                        |
|:-------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:|
| <img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/Remote_Config_Before.png" width="216" height="384" /> | <img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/UserFormBeforeAgeValues.png" width="216" height="384" /> | <img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/UserForm_after.png" width="216" height="384" /> |


|                                          User Form after Age values after update                                       |                                               User Form Values Submitted                                              | 
|:----------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------:|
|<img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/UserFormAfterAgeValues.png" width="432" height="768" /> |<img src="https://github.com/saichandu415/Table-Store-Android-Sample/raw/master/screenshots/UserForm_Submitted_Values.png" width="432" height="768" />|


## License

    Copyright (c) 2017-present, Sarath Chandra.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
