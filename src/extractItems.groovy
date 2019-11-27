 if (args.size() == 0) {
    println "Usage: groovy extractItems.groovy XMLFILE-IN-GSA-FORMAT.xml"
    System.exit(1)
}
def gsaFile = new XmlSlurper().parseText(new File(args[0]).text)
def desc = gsaFile.'item-descriptor'
def processItemDescriptor(myDesc) {
    def info = [:]
    def tables = []
    def props = [:]
    def descriptors = [:]
    for (table in myDesc.table) {
        String tableName = table.@name.text()
        tables += tableName
        // collect table's properties
        for (prop in table.property) {
            String name = "${prop.@name.text()}(${tableName})".replaceAll(/[ -]/, '_')
            props[name] = [:]
            if ("${prop.@'required'.text()}" != "") {
                props[name]['required'] = "${prop.@'required'.text()}"
            }
            if ("${prop.@'hidden'.text()}" != "") {
                props[name]['hidden'] = "${prop.@'hidden'.text()}"
            }
 
            if ("${prop.@'item-type'.text()}" != "") {
                props[name]['item-type'] = "${prop.@'item-type'.text()}"
                descriptors["${prop.@name.text()}"] = "${prop.@'item-type'.text().replaceAll(/[ -]/, '_')}"
            }
        }
    }
    info['tables'] = tables
    // scan for properties outside of tables
    for (prop in myDesc.property) {
        String name = "${prop.@name.text()}".replaceAll(/[ -]/, '_')
        props[name] = [:]
        if ("${prop.@'item-type'.text()}" != "") {
            props[name]['item-type'] = "${prop.@'item-type'.text()}"
        }      
        }
    info['properties'] = props
    info['descriptors'] = descriptors
    return info
}
 
println "Got ${desc.size()} item-descriptors"
 
println "@startuml"
for (idesc in desc) {
    def info = processItemDescriptor(idesc)
    def name = "${idesc.@name.text().replaceAll(/[ -]/, '_')}"
    println "\nclass  ${name} {"
    for (p in info.tables)
        println "\t"+p
    println ".."   
    for (p in info.properties)
        println "\t"+p
    println "}"
    for (p in info.descriptors?.keySet())
        println "\t ${name} *-- ${info.descriptors[p]} : ${p}"
}
println "@enduml"