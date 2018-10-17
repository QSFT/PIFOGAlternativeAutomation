Feature: test

Scenario: selenium test
Given open browser
And navigate to 'http://10.30.168.38:8080'
And login FMS
And open 'Homes -> Databases' dashboard
Then 'Databases' dashboard should show up
Then the 'All > All Instances' grid should contain following rows:
	|[colspan]Name | [colspan]Version   | Host | [colspan]CPU Load (%)| [colspan]Memory (%)| [colspan]Disk (% Busy)|
	|10.30.169.132 | 14.0.1000.169 | fog-2253-win201 | >=0 | >=0 | >=0 |
Then check following rows in 'All > All Instances' grid:
	|[colspan]Name | [colspan]Version   | Host | 
	|10.30.169.132 | 14.0.1000.169 | fog-2253-win201 | 
And on 'All > All Instances' grid click 'BlueEyeIcon.png' icon in the following row:
	|[colspan]Name | [colspan]Version   | Host | 
	|10.30.169.132 | 14.0.1000.169 | fog-2253-win201 | 
And click 'Monitor' button
Then a popup should show up
And click 'SQL Server' text in the popup
Then 'Monitor SQL Server Instance' dialog should show up

And type '10.30.169.132' into 'Server name' text field
And type 'administrator' into 'User name' text field

Then select 'Active Directory (AD) Authentication' in the 'Specify login credentials' dropdownlist

And click 'Use the Active Directory account running your agent manager' radio in 'Monitor SQL Server Instance' dialog
#And click 'YouTube-social-squircle_red20x20.png' icon in 'Monitor SQL Server Instance' dialog
And check the checkbox before 'SQL Performance Investigator' in 'Monitor SQL Server Instance' dialog
And check the checkbox before 'Collect VM statistics' in 'Monitor SQL Server Instance' dialog
#And uncheck the checkbox after 'Operating System' in 'Monitor SQL Server Instance' dialog
And click the 'Correlate SQL Server system resources' link
Then 'Operating System Monitoring Extension' dialog should show up
And click 'Log in to the host using different login credentials' radio in 'Operating System Monitoring Extension' dialog
And select 'SSH (login credentials)' in the 'Authentication' dropdownlist in 'Operating System Monitoring Extension' dialog
And type 'administrator' into 'Username' text field in 'Operating System Monitoring Extension' dialog
And type 'administrator' into 'Password' text field in 'Operating System Monitoring Extension' dialog
And click 'Cancel' button in 'Operating System Monitoring Extension' dialog
And uncheck the checkbox before 'Automatically generate agent name'
And wait 3 seconds
And click 'Cancel' button in 'Monitor SQL Server Instance' dialog

And click 'Oracle' tile
And click 'SQL Server' tile on 'Databases' dashboard
#And 'Agent Status' screen grid should contain next rows in 180 seconds:
#			|Collecting Data | Agent Name   | Agent Manager | Type |
#     |[img]start16.png | DefaultNexus | zhuvm-fog-2219 |Nexus |
#		 |[img]start16.png| EmbeddedHostMonitor | zhuvm-fog-2219 |WindowsAgent |
And wait 3 seconds
#Then close browser
