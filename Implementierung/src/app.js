require('./assets/stylesheet.css')
require('file-loader?name=[name].[ext]!../node_modules/neo4j-driver/lib/browser/neo4j-web.min.js');
var _ = require('lodash');

var neo4j = window.neo4j.v1;
var driver = neo4j.driver("bolt://localhost", neo4j.auth.basic("neo4j", "KaSc1905"));

var nodes = [];
var edges = [];
var userClasses = [];

var stm = [];
stm.push('match (info:Informationssystem) return info');
stm.push('match (orga:Organisationseinheit) return orga');
stm.push('match (pers:Person) return pers');
stm.push('match (fachProz:Fachprozess) return fachProz');
stm.push('match (standard:Standard) return standard');
stm.push('match (standort:Standort) return standort');
stm.push('match (tech:Technologie) return tech');
stm.push('match (anza:Anzahl_Anwender) return anza');

var rel = [];
rel.push('match (source)-[:Eingesetzt_In]->(target) return source, target');
rel.push('match (source)-[:besitzt]->(target) return source, target');
rel.push('match (source)-[:gehoert_zu]->(target) return source, target');
rel.push('match (source)-[:integriert]->(target) return source, target');
rel.push('match (source)-[:liegt_am]->(target) return source, target');
rel.push('match (source)-[:verantwortlich]->(target) return source, target');
rel.push('match (source)-[:verwendet_Standard]->(target) return source, target');
rel.push('match (source)-[:verwendet_Technologie]->(target) return source, target');
  

getNodesFromDB(stm)
.then(resNodes => getRelationsFromDB(rel, resNodes))
.then(res => getGraph(res))
.then(g => {
  nodes = g.nodes;
  edges = g.edges;
  userClasses = g.userClasses;


  //defining the chart
  var myChart = graphChart().nodes(nodes)
    .links(edges);

  //defining the width and height of the svg
  var width = window.innerWidth;
  var height = window.innerHeight;

  //drawing the svg and calling the graphChart opject.

  d3.select('#graph')
    .append("div")
    .classed("svg-container", true)
    .append("svg")
    .attr("preserveAspectRatio", "xMinYMin meet")
    .attr("viewBox", "0 0 " + width + " " + height)
    //.attr("viewBox", "0 0 600 400")
    .classed("svg-content-responsive", true)
    //.attr("width", width)
    //.attr("height", height)
    //.attr("background-color", "yellow")
    .call(myChart);



  function graphChart() {


    var nodes = [], links = [];

    function my(svg) {

      svg.append("circle").attr("cx",100).attr("cy",100).attr("r", 6).attr("class", "info");
      svg.append("text").attr("x", 120).attr("y", 105).text("Informationssystem").style("font-size", "15px").attr("alignment-baseline","middle")
      svg.append("circle").attr("cx",100).attr("cy",130).attr("r", 6).attr("class", "orga");
      svg.append("text").attr("x", 120).attr("y", 135).text("Organisationseinheit").style("font-size", "15px").attr("alignment-baseline","middle")
      svg.append("circle").attr("cx",100).attr("cy",160).attr("r", 6).attr("class", "tech");
      svg.append("text").attr("x", 120).attr("y", 165).text("Technologie").style("font-size", "15px").attr("alignment-baseline","middle")
      svg.append("circle").attr("cx",100).attr("cy",190).attr("r", 6).attr("class", "pers");
      svg.append("text").attr("x", 120).attr("y", 195).text("Person").style("font-size", "15px").attr("alignment-baseline","middle")
      svg.append("circle").attr("cx",100).attr("cy",220).attr("r", 6).attr("class", "fachProz");
      svg.append("text").attr("x", 120).attr("y", 225).text("Fachprozess").style("font-size", "15px").attr("alignment-baseline","middle")
      svg.append("circle").attr("cx",100).attr("cy",250).attr("r", 6).attr("class", "Standard");
      svg.append("text").attr("x", 120).attr("y", 255).text("Standard").style("font-size", "15px").attr("alignment-baseline","middle")
      svg.append("circle").attr("cx",100).attr("cy",280).attr("r", 6).attr("class", "Standort");
      svg.append("text").attr("x", 120).attr("y", 285).text("Standort").style("font-size", "15px").attr("alignment-baseline","middle")
      



      var repelForce = d3.forceManyBody().strength(-500).distanceMax(450)
        .distanceMin(200);

      var simulation = d3.forceSimulation()
        .force("center", d3.forceCenter(width / 2, height / 2))
        .force("charge", d3.forceManyBody().strength(-100))
        .force("xAxis", d3.forceX(width / 2).strength(0.4))
        .force("yAxis", d3.forceY(height / 2).strength(1.2))
        .force("repelForce", repelForce)
        .force("link", d3.forceLink())
        .force("collide", d3.forceCollide().radius(function(d) {return (d.radius+10)}).iterations(10).strength(1))

      var anzAnwenderEdges = []
      edges.forEach(res => {

        if(res.type == "besitzt") {
          anzAnwenderEdges.push(res)
        }
      });

      edges = edges.filter( ( el ) => !anzAnwenderEdges.includes( el ) );

      anzAnwenderEdges.forEach(c => {
        switch(nodes[c.target].Klasse) {
          case "XXS (1)": nodes[c.source].radius = 5; break;
          case "XS (10)": nodes[c.source].radius = 10; break;
          case "S (50)": nodes[c.source].radius = 15; break;
          case "M (100)": nodes[c.source].radius = 25; break;
          case "L (500)": nodes[c.source].radius = 35; break;
          case "XL (1000)": nodes[c.source].radius = 40; break;
          case "XXL (10000)": nodes[c.source].radius = 50; break;
          case "unbekannt": nodes[c.source].radius = 8; break;
        }
      })

      var links = svg.selectAll("foo")
        .data(edges)
        .enter()
        .append("line")
        .attr("class", function (d) {
          switch (d.type) {
            case "Eingesetzt_In": return "Eingesetzt_In";
            case "besitzt": return "besitzt";
            case "gehoert_zu": return "gehoert_zu";
            case "integriert": return "integriert";
            case "liegt_am": return "liegt_am";
            case "verantwortlich": return "verantwortlich";
            case "verwendet_Standard": return "verwendet_Standard";
            case "verwendet_Technologie": return "verwendet_Technologie";
            default: return "unknown";
          }
        })      

      nodes = nodes.filter( ( el ) => !userClasses.includes( el ) );

      //draw the nodes with drag functionality
      var node = svg.selectAll("foo")
        .data(nodes)
        .enter()
        .append("g")
        .call(d3.drag()
          .on("start", dragstarted)
          .on("drag", dragged)
          .on("end", dragended));

      var tooltip = d3.select("body")
        .append("div")
        .attr("class", "tooltip")
        .html("");


      node.append("circle")
        .attr("class", function (d) {
          switch (d.type) {
            case "Informationssystem": return "not-fixed info";
            case "Organisationseinheit": return "not-fixed orga";
            case "Technologie": return "not-fixed tech";
            case "Anzahl_Anwender": return "not-fixed anza";
            case "Person": return "not-fixed pers";
            case "Fachprozess": return "not-fixed fachProz";
            case "Standard": return "not-fixed standard";
            case "Standort": return "not-fixed standort";
            default: return "unknown";
          }
        })
        .attr("r", function (d) {
          if (isNaN(d.radius)) {
            d.radius = 25;
          }
          return d.radius
        })

        .on("mouseover", function (d) {
          var t_text = "empty";
          if (d.type !== " ") {
            switch (d.type) {
              case "Informationssystem":
                t_text = "<strong>" + (d.Name) + "</strong>";
                t_text = t_text + "<br>Beschreibung: " + d.Beschreibung;
                t_text = t_text + "<br>Anzahl Installationen: " + d.Anzahl_Installationen;
                t_text = t_text + "<br>Subsysteme: " + d.Subsysteme;
                t_text = t_text + "<br>Investitionsgroesse: " + d.Investitionsgroesse;
                t_text = t_text + "<br>Eingesetzt seit: " + d.Eingesetzt_seit;
                t_text = t_text + "<br>Type: " + d.type;
                break;
              case "Organisationseinheit":
                t_text = "<strong>" + (d.Name) + "</strong>";
                t_text = t_text + "<br>Uebergeordnete Einheit: " + d.Uebergeordnete_Einheit;
                t_text = t_text + "<br>Type: " + d.type;
                break;
              case "Technologie":
                t_text = "<strong>" + (d.Name) + "</strong>";
                t_text = t_text + "<br>Beschreibung: " + d.Beschreibung;
                t_text = t_text + "<br>End of life: " + d.EndOfLife;
                t_text = t_text + "<br>Type: " + d.type;
                break;
              case "Person":
                t_text = "<strong>" + (d.Name) + "</strong>";
                t_text = t_text + "<br>Type: " + d.type;
                break;
              case "Fachprozess":
                t_text = "<strong>" + (d.Name) + "</strong>";
                t_text = t_text + "<br>Standardkonformität: " + d.Standardkonformität;
                t_text = t_text + "<br>Type: " + d.type;
                break;
              case "Standard":
                t_text = "<strong>" + (d.Art) + "</strong>";
                t_text = t_text + "<br>Name: " + d.Name;
                t_text = t_text + "<br>Type: " + d.type;
                break;
              case "Standort":
                t_text = "<strong>" + (d.Name) + "</strong>";
                t_text = t_text + "<br>PLZ: " + d.PLZ;
                t_text = t_text + "<br>Anschrift: " + d.Anschrift;
                t_text = t_text + "<br>Type: " + d.type;
                break;
              case "Anzahl":
                t_text = "<strong>TEST</strong>";
                break;
               
            }

            tooltip.html(t_text)
            return tooltip.style("visibility", "visible");
          }
        })
        .on("mousemove", function () { return tooltip.style("top", (event.pageY - 10) + "px").style("left", (event.pageX + 10) + "px"); })
        .on("mouseout", function () { return tooltip.style("visibility", "hidden"); });


      //append labels
      node.append("text")
        .style("fill", "black")
        .attr("dx", 0)
        .attr("dy", 2)
        .attr("text-anchor", "middle")
        .text(function (d) {
          return (d.Name);
        });

      //finally - attach the nodes and the links to the simulation
      simulation.nodes(nodes);
      simulation.force("link").links(edges);

      //and define tick functionality
      simulation.on("tick", function () {

        links.attr("x1", function (d) { return d.source.x; })
          .attr("y1", function (d) { return d.source.y; })
          .attr("x2", function (d) { return d.target.x; })
          .attr("y2", function (d) { return d.target.y; })

        node.attr("transform", function (d) { return "translate(" + d.x + "," + d.y + ")" })
      });

      function dragstarted(d) {

        if (!d3.event.active) simulation.alphaTarget(0.3).restart();

        d.fx = d.x;
        d.fy = d.y;

        //stickiness - toggles the class to fixed/not-fixed to trigger CSS
        var my_circle = d3.select(this).selectAll('circle')
        var classes = my_circle.attr('class').split(' ');

        if (my_circle.attr('class') == "fixed " + classes[1]) {
          my_circle.attr("class", "not-fixed " + classes[1])
        } else {
          my_circle.attr("class", "fixed " + classes[1])
        }
      }

      function dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
      }

      function dragended(d) {
        if (!d3.event.active) simulation.alphaTarget(0);
        //stickiness - unfixes the node if not-fixed or a person
        var my_circle = d3.select(this).selectAll('circle')
        var classes = my_circle.attr('class').split(' ');
        if (my_circle.attr('class') == ("not-fixed " + classes[1])) {
          d.fx = null;
          d.fy = null;
        }

      }


    }

    my.width = function (value) {
      if (!arguments.length) return width;
      width = value;
      return my;
    };

    my.nodes = function (value) {
      if (!arguments.length) return nodes;
      nodes = value;
      return my;
    };

    my.links = function (value) {
      if (!arguments.length) return links;
      links = value;
      return my;
    };

    my.height = function (value) {
      if (!arguments.length) return height;
      height = value;
      return my;
    };

    return my;

  }


});

function parseCypherToDB(statement) {
  return new Promise(resolve => {
    var session = driver.session();
    var results = session.run(statement);
    session.close();
    resolve(results);
  })
}

function getGraph(res) {

  var nodes = [], edges = [],  userClasses = [];

  nodes = generateNodes(res.resNodes);

  nodes.forEach(n =>{
    if(n.type == 'Anzahl') {
      userClasses.push(n);
    }
  });
  
  edges = generateRelations(res.resRelations, nodes);

  return { nodes, edges, userClasses };
}


function generateNodes(results) {
  var nodes = [];

  results.forEach(r => {
    r.records.forEach(record => {
      var node = recordToNode(record.get(record.keys[0]));
      nodes.push(node);
    })    
  })

  return nodes;
}

function recordToNode(record) {
  var i;
  switch (record.labels[0]) {
    case 'Informationssystem':
      i = {
        Anzahl_Installationen: record.properties.Anzahl_Installationen,
        Beschreibung: record.properties.Beschreibung,
        Code: record.properties.Code,
        Eingesetzt_seit: record.properties.Eingesetzt_seit,
        Investitionsgroesse: record.properties.Investitionsgroesse,
        Name: record.properties.Name,
        Subsysteme: record.properties.Subsysteme,
        type: "Informationssystem"
      };
      break;
    case 'Organisationseinheit':
      i = {
        Code: record.properties.Code,
        Name: record.properties.Name,
        type: "Organisationseinheit"
      };
      break;
    case 'Person':
      i = {
        Name: record.properties.Name,
        type: "Person"
      };
      break;
    case 'Technologie':
      i = {
        Code: record.properties.Code,
        Name: record.properties.Name,
        Beschreibung: record.properties.Beschreibung,
        EndOfLife: record.properties.EndOfLife,
        type: "Technologie"
      };
      break;
    case 'Anzahl_Anwender':
      i = {
        Klasse: record.properties.Klasse,
        type: "Anzahl"
      };
      break;
    case 'Standard':
      i = {
        Code: record.properties.Code,
        Name: record.properties.Name,
        Art: record.properties.Art,
        type: "Standard"
      };
      break;
    case 'Standort':
      i = {
        Code: record.properties.Code,
        Name: record.properties.Name,
        PLZ: record.properties.PLZ,
        Anschrift: record.properties.Anschrift,
        type: "Standort"
      };
      break;
    case 'Fachprozess':
      i = {
        Code: record.properties.Code,
        Name: record.properties.Name,
        Verantwortlich: record.properties.Verantwortlich,
        type: "Fachprozess"
      };
      break;
  }
  return i;
}

function parseStatementArray(stm) {
  return new Promise(resolve => {
    var test = [];
    stm.reduce((chain, currentStatement) => {
      var t = chain.then(() => parseCypherToDB(currentStatement));
      test.push(t);
      return t;
    },Promise.resolve())

  
    resolve(Promise.all(test))
  })
}

function getNodesFromDB(stm) {
  return new Promise(resolve => {
    parseStatementArray(stm).then(resNodes => {
      resolve(resNodes);
    })
  })
}

function getRelationsFromDB(stm, resNodes) {
  return new Promise(resolve => {
    parseStatementArray(stm).then(resRelations => {
      resolve({resNodes, resRelations});
    })
  })
}

function generateRelations(results, nodes) {
  var edges = [];
  results.forEach(result => {
    result.records.forEach(relation => {
      var source = _.findIndex(nodes, recordToNode(relation.get('source')));
      var target = _.findIndex(nodes, recordToNode(relation.get('target')));
      var type;
      var test = [nodes[source].type, nodes[target].type]
      
      switch (test.toString()) {
        case ['Informationssystem', 'Organisationseinheit'].toString():
          type = 'Eingesetzt_In';
          break;
        case ['Informationssystem', 'Anzahl'].toString():
          type = 'besitzt';
          break;
        case ['Organisationseinheit', 'Organisationseinheit'].toString():
          type = 'gehoert_zu';
          break;
        case ['Fachprozess', 'Informationssystem'].toString():
          type = 'integriert';
          break;
        case ['Organisationseinheit', 'Standort'].toString():
          type = 'liegt_am';
          break;
        case ['Person', 'Informationssystem'].toString():
          type = 'verantwortlich';
          break;
        case ['Informationssystem', 'Standard'].toString():
          type = 'verwendet_Standard';
          break;
        case ['Informationssystem', 'Technologie'].toString():
          type = 'verwendet_Technologie';
          break;
      }
      edges.push({source, target, type});
    });
  });
  return edges
}
