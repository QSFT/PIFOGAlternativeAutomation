Feature: 10.30.169.32

Scenario: Go to PI view 10.30.169.32
#Given get FMS access token
Given open browser
And navigate to 'http://10.30.169.152:8080'
And login FMS
And open 'Homes -> Databases - SQL PI' dashboard
#And click the 3rd 'SPI_Samll.png' icon
And on 'SQL PI Monitoring' grid click 'SPI_Samll.png' icon in the following row:
|[colspan]Name|
| 10.30.169.32|
And set AlternativeInstancePerformance '10.30.168.117-10.30.169.32'

Scenario: SQL PI page 1h
And set AlternativeInstancePerformance '10.30.168.117-10.30.169.32'
And on SQL PI page, change time range to last 1h

And on SQL PI page, click 'Workload' demension

And on SQL PI page, click 'Instance View' node in Performance Tree
And on SQL PI page, click 'SQL Statements' node in Performance Tree
And on SQL PI page, click 'TSQL Batches' node in Performance Tree
And on SQL PI page, click 'Databases' node in Performance Tree
And on SQL PI page, click 'Programs' node in Performance Tree
And on SQL PI page, click 'Users' node in Performance Tree
And on SQL PI page, click 'Client Machines' node in Performance Tree
And on SQL PI page, click 'Context Infos' node in Performance Tree
And on SQL PI page, click 'Command Types' node in Performance Tree
And on SQL PI page, click 'Sessions' node in Performance Tree
And on SQL PI page, click 'Locked Objects' node in Performance Tree
And on SQL PI page, click 'Objects I/O' node in Performance Tree
And on SQL PI page, click 'Files' node in Performance Tree
And on SQL PI page, click 'Disks' node in Performance Tree

Scenario: SQL PI page 4h
And set AlternativeInstancePerformance '10.30.168.117-10.30.169.32'
And on SQL PI page, change time range to last 4h

And on SQL PI page, click 'Memory' demension

And on SQL PI page, click 'Instance View' node in Performance Tree
And on SQL PI page, click 'SQL Statements' node in Performance Tree
And on SQL PI page, click 'TSQL Batches' node in Performance Tree
And on SQL PI page, click 'Databases' node in Performance Tree
And on SQL PI page, click 'Programs' node in Performance Tree
And on SQL PI page, click 'Users' node in Performance Tree
And on SQL PI page, click 'Client Machines' node in Performance Tree
And on SQL PI page, click 'Context Infos' node in Performance Tree
And on SQL PI page, click 'Command Types' node in Performance Tree
And on SQL PI page, click 'Sessions' node in Performance Tree

Scenario: SQL PI page 8h
And set AlternativeInstancePerformance '10.30.168.117-10.30.169.32'
And on SQL PI page, change time range to last 8h

And on SQL PI page, click 'CPU' demension

And on SQL PI page, click 'Instance View' node in Performance Tree
And on SQL PI page, click 'SQL Statements' node in Performance Tree
And on SQL PI page, click 'TSQL Batches' node in Performance Tree
And on SQL PI page, click 'Databases' node in Performance Tree
And on SQL PI page, click 'Programs' node in Performance Tree
And on SQL PI page, click 'Users' node in Performance Tree
And on SQL PI page, click 'Client Machines' node in Performance Tree
And on SQL PI page, click 'Context Infos' node in Performance Tree
And on SQL PI page, click 'Command Types' node in Performance Tree
And on SQL PI page, click 'Sessions' node in Performance Tree

Scenario: SQL PI page 24h
And set AlternativeInstancePerformance '10.30.168.117-10.30.169.32'
And on SQL PI page, change time range to last 24h

And on SQL PI page, click 'I/O' demension

And on SQL PI page, click 'Instance View' node in Performance Tree
And on SQL PI page, click 'SQL Statements' node in Performance Tree
And on SQL PI page, click 'TSQL Batches' node in Performance Tree
And on SQL PI page, click 'Databases' node in Performance Tree
And on SQL PI page, click 'Programs' node in Performance Tree
And on SQL PI page, click 'Users' node in Performance Tree
And on SQL PI page, click 'Client Machines' node in Performance Tree
And on SQL PI page, click 'Context Infos' node in Performance Tree
And on SQL PI page, click 'Command Types' node in Performance Tree
And on SQL PI page, click 'Sessions' node in Performance Tree
And on SQL PI page, click 'Objects I/O' node in Performance Tree
And on SQL PI page, click 'Files' node in Performance Tree
And on SQL PI page, click 'Disks' node in Performance Tree

Scenario: SQL PI page 48h
And set AlternativeInstancePerformance '10.30.168.117-10.30.169.32'
And on SQL PI page, change time range to last 48h

And on SQL PI page, click 'Network' demension

And on SQL PI page, click 'Instance View' node in Performance Tree
And on SQL PI page, click 'SQL Statements' node in Performance Tree
And on SQL PI page, click 'TSQL Batches' node in Performance Tree
And on SQL PI page, click 'Databases' node in Performance Tree
And on SQL PI page, click 'Programs' node in Performance Tree
And on SQL PI page, click 'Users' node in Performance Tree
And on SQL PI page, click 'Client Machines' node in Performance Tree
And on SQL PI page, click 'Context Infos' node in Performance Tree
And on SQL PI page, click 'Command Types' node in Performance Tree
And on SQL PI page, click 'Sessions' node in Performance Tree

Scenario: SQL PI page 72h
And set AlternativeInstancePerformance '10.30.168.117-10.30.169.32'
And on SQL PI page, change time range to last 72h

And on SQL PI page, click 'Lock' demension

And on SQL PI page, click 'Instance View' node in Performance Tree
And on SQL PI page, click 'SQL Statements' node in Performance Tree
And on SQL PI page, click 'TSQL Batches' node in Performance Tree
And on SQL PI page, click 'Databases' node in Performance Tree
And on SQL PI page, click 'Programs' node in Performance Tree
And on SQL PI page, click 'Users' node in Performance Tree
And on SQL PI page, click 'Client Machines' node in Performance Tree
And on SQL PI page, click 'Context Infos' node in Performance Tree
And on SQL PI page, click 'Command Types' node in Performance Tree
And on SQL PI page, click 'Sessions' node in Performance Tree
And on SQL PI page, click 'Locked Objects' node in Performance Tree

Scenario: SQL PI page All
And set AlternativeInstancePerformance '10.30.168.117-10.30.169.32'
And on SQL PI page, change time range to last All

And on SQL PI page, click 'Latch' demension

And on SQL PI page, click 'Instance View' node in Performance Tree
And on SQL PI page, click 'SQL Statements' node in Performance Tree
And on SQL PI page, click 'TSQL Batches' node in Performance Tree
And on SQL PI page, click 'Databases' node in Performance Tree
And on SQL PI page, click 'Programs' node in Performance Tree
And on SQL PI page, click 'Users' node in Performance Tree
And on SQL PI page, click 'Client Machines' node in Performance Tree
And on SQL PI page, click 'Context Infos' node in Performance Tree
And on SQL PI page, click 'Command Types' node in Performance Tree
And on SQL PI page, click 'Sessions' node in Performance Tree


#Then close browser
