# atg-repository-visualizer

## What is this
Script to generate visual representation of an ATG (== Oracle Commerce these days) repository structure for documentation purposes.

The script does parse the XML files (scraped from dyn-admin, or output of the XML combine) and produces file in the syntax of Plant UML - http://plantuml.sourceforge.net

This is groovy script that I found when cleaning up old Mac - I am putting it here so that I can find it again.

# Using it

The generation requires installed dot tools

Mine is installed in `/opt/diagram/plantuml.jar`

```
groovy ./src/extractItems.groovy pricing-generated.xml >pricing.uml
groovy ./src/extractItems.groovy catalog-generated.xml >catalog.uml
  
java -jar /opt/diagram/plantuml.jar catalog.uml
java -jar /opt/diagram/plantuml.jar pricing.uml
```

The input XML files are result of scraping the repository structure in DynAdmin Web page. 

See e.g. [this](http://learnoracleatg.blogspot.com/2015/03/art211-how-to-query-atg-repository.html)

If the above does not sense to you, do not worry, you probably do not need this tool anyway.

# Demo

See the files in `examples`:

## Catalog
![](/example/catalog.png)

The file is just for ilustration how does output look like.

The XML and UML files are incomplete - removed proprietary information.



See also [my old blog](http://www.miroadamy.com/posts/2014-11-04-atg-repo-visualizer/)