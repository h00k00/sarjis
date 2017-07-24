#!/usr/bin/python

import sys
import json

print ('Number of arguments:', len(sys.argv), 'arguments.')
print ('Argument List:', str(sys.argv))

lines = [line.strip() for line in open(sys.argv[1])]

resultFile = open(sys.argv[2],'a')


lines[:] = [line for line in lines if line.find("a class")>=0 or line.find("<h1>") >= 0 or line.find("</body>") >= 0 or line.find("<br />") >= 0]

for line in lines:
	line = line.replace("> ", ">")
	line = line.replace("<h1>", "{ \"lehdennimi\" : \"")
	line = line.replace("</h1>", "\",\n\"tarinalista\" : [\n")
	line = line.replace("<a class=\"tekija\" href=", "]},\n{ \"tekija\" : \"")
	line = line.replace("<li>", "},\n{\"numero\" : \"")
	line = line.replace("<a class=\"alkupnimi\">", "\", \"alkupnimi\" : \"")
	line = line.replace("<a class=\"nimike\">", "\",\n \"nimike\" : \"")
	line = line.replace("<a class=\"julk\">", "\",\n  \"julk\" : \"")
	line = line.replace("<tr><td></td><td></td></tr>", "")
#	line = line.replace("</td><td></td></tr>", "\",\n\"tarinat\" : [\n")
	line = line.replace("</td></tr>", "\"},\n")
	line = line.replace("</a></li>", "\"")
	line = line.replace("</a>", "")
	line = line.replace("</li>", "")
	line = line.replace("<br/>", "")
	line = line.replace("<br />", "")
	line = line.replace("(", "")
	line = line.replace(")", "")
	line = line.replace("</html>", "")
	line = line.replace("</body>", "]\n}\n]\n}")
	print (line)
	resultFile.write(line)

resultFile.close()


