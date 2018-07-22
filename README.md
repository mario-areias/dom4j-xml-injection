# dom4j-xml-injection

This is a proof of concept to show a XML injection vulnerability on dom4j.

This project has two submodules that are identical except by the dom4j version. The vulnerable module uses 2.1.0 and the safe module uses 2.1.1.

Both modules have the same tests describing the vulnerability. The test goes green on the vulnerable module but it fails on the safe module.

To check the resulting XML file for the vulnerable module, just look for the output file at `./vulnerable-version/output.xml`