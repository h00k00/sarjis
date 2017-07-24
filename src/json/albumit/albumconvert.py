#!/usr/bin/python

import sys
import json

print ('Number of arguments:', len(sys.argv), 'arguments.')
print ('Argument List:', str(sys.argv))

lines = [line.strip() for line in open(sys.argv[1])]

resultFile = open(sys.argv[2],'a')


lines[:] = [line for line in lines if line.find("td class")>=0 or line.find("<h1>") >= 0 or line.find("</table>") >= 0]

for line in lines:
	line = line.replace("> ", ">")
	line = line.replace("<h1>", "{ \"lehdennimi\" : \"")
	line = line.replace("</h1>", "\",\n\"tarinalista\" : [\n")
	line = line.replace("<td class=\"tekija\">", "]},\n{ \"tekija\" : \"")
	line = line.replace(":</td>", "\",\n\"tarinat\": [")
	line = line.replace("<td class=\"alkupnimi\">", ",\n  \"alkupnimi\" : \"")
	line = line.replace("<td class=\"nimike\">", "},\n  {\"nimike\" : \"")
	line = line.replace("<td class=\"julk\">", ",\n    \"julk\" : \"")
	line = line.replace("<tr><td></td><td></td></tr>", "")
	line = line.replace("</td><td></td></tr>", "\",\n\"tarinat\" : [\n")
	line = line.replace("</td></tr>", "\"},\n")
	line = line.replace("</td>", "\"")
	line = line.replace("</tr>", "\",")
	line = line.replace("<tr>", "\",")
	line = line.replace("<br />", "")
	line = line.replace("&amp;", "&")
	line = line.replace("</table>", "\n}\n")
	print (line)
	resultFile.write(line)

resultFile.close()


