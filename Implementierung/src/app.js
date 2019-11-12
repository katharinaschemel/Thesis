//var api = require('./neo4jApi');
const css = require('./assets/stylesheet.css')
require('file?name=[name].[ext]!../node_modules/neo4j-driver/lib/browser/neo4j-web.min.js');
//var Movie = require('./models/Movie');
//var MovieCast = require('./models/MovieCast');
var _ = require('lodash');

var neo4j = window.neo4j.v1;
var driver = neo4j.driver("bolt://localhost", neo4j.auth.basic("neo4j", "KaSc1905"));

//var DBResult = parseCypherToDB('MATCH (m:Movie)<-[:ACTED_IN]-(a:Person) RETURN m.title AS movie, collect(a.name) AS actor LIMIT {limit}');
//var records = DBResult.records;
var nodes = [];
var edges = [];
var userClasses = [];


//defining the width and height of the svg
var width = window.innerWidth;// default width
var height = window.innerHeight;

getData().then(g => {
  nodes = g.nodes;
  edges = g.edges;
  userClasses = g.userClasses;


  //defining the chart
  var myChart = familyChart().nodes(nodes)
    .links(edges);

  //defining the width and height of the svg
  var width = window.innerWidth; // default width
  var height = window.innerHeight;

  //drawing the svg and calling the familyChart opject.

  var svg = d3.select('#graph').append("svg")
    .attr("width", width)
    .attr("height", height)
    .attr("background-color", "yellow")
    .call(myChart);



  function familyChart() {


    var nodes = [], links = [] // default height

    function my(svg) {


      var repelForce = d3.forceManyBody().strength(-1000).distanceMax(450)
        .distanceMin(120);

      var simulation = d3.forceSimulation()
        //     .alphaDecay(0.04)
        //     .velocityDecay(0.4)
        .force("center", d3.forceCenter(width / 2, height / 2))
        .force("charge", d3.forceManyBody().strength(-100))
        .force("xAxis", d3.forceX(width / 2).strength(0.4))
        .force("yAxis", d3.forceY(height / 2).strength(0.6))
        .force("repelForce", repelForce)
        .force("link", d3.forceLink().distance(200))
      //.force("collide", d3.forceCollide().radius(function(d) {return d.radius}))


      /* function dist(d){
      //used by link force
      return 100

      } */

      /* var anzAnwenderEdges = []
      edges.forEach(res => {

        if(res.type == "Anzahl_Anwender")
          anzAnwenderEdges.push(res)
          edges.splice(edges.indexOf(res), 1)
      }); */

      var links = svg.selectAll("foo")
        .data(edges)
        .enter()
        .append("line")
        .attr("class", function (d) {
          switch (d.type) {
            case "Organisationseinheit": return "edgeOrga";
            case "Technologie": return "edgeTech";
            case "Anzahl_Anwender": return "edgeAnza";
            case "Person": return "edgePers";
            default: return "unknown";
          }
        })
      //.attr("stroke-width", "4px")
      //.attr("stroke-dasharray", "6,6")
      //.attr("stroke", "gold");
      userClasses.forEach(c => {
        switch (c.anzaClass) {
          case "XXS (1)": nodes[c.source].radius = 5; break;
          case "XS (10)": nodes[c.source].radius = 10; break;
          case "S (50)": nodes[c.source].radius = 15; break;
          case "M (100)": nodes[c.source].radius = 25; break;
          case "L (500)": nodes[c.source].radius = 35; break;
          case "XL (1000)": nodes[c.source].radius = 40; break;
          case "XXL (10000)": nodes[c.source].radius = 45; break;
          case "unbekannt": nodes[c.source].radius = 60; break;
        }
      })


      //draw the nodes with drag functionality
      var node = svg.selectAll("foo")
        .data(nodes)
        .enter()
        .append("g")
        .call(d3.drag()
          .on("start", dragstarted)
          .on("drag", dragged)
          .on("end", dragended));

      /*var tooltip = d3.select("body")
          .append("div")
          .attr("class", "tooltip")
          .html("");
      */

      node.append("circle")
        .attr("class", function (d) {
          switch (d.type) {
            case "Informationssystem": return "not-fixed info";
            case "Organisationseinheit": return "not-fixed orga";
            case "Technologie": return "not-fixed tech";
            case "Anzahl_Anwender": return "not-fixed anza";
            case "Person": return "not-fixed pers";
            default: return "unknown";
          }
        })
        .attr("r", function (d) {
          if (isNaN(d.radius)) {
            d.radius = 10;
          }
          return d.radius
        })


      //if(d.type == "Informationssystem") {return "fixed info"} else {return "not-fixed"}})

      //.attr("fill", function(d) {if(d.label == "movie"){ return "blue"} else{ return "red"}})
      //attr("stroke", "gold")
      //.attr("stroke-width","2px")
      /* .on("mouseover", function(d){
               if(d.type !== "family"){
                 //sets tooltip.  t_text = content in html
                 t_text = "<strong>" + titleCase(d.name) + "</strong><br>Age: " + d.age
                 if(d.profession !== undefined){
                   //only add profession if it is defined
                   t_text += "<br>Profession: " + d.profession}
                 tooltip.html(t_text)
                 return tooltip.style("visibility", "visible");
               }  })
         .on("mousemove", function(){return tooltip.style("top", (event.pageY-10)+"px").style("left",(event.pageX+10)+"px");})
         .on("mouseout", function(){return tooltip.style("visibility", "hidden");});
       */

      //append labels
      //var texts = node.append("text")
      //  .style("fill", "black")
      //  .attr("dx", 0)
      //  .attr("dy", 50)
      //  .attr("text-anchor","middle")
      //  .text(function(d) {
      //      return titleCase(d.title);
      //  });

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
        //if(d.type == 'Informationssystem'){
        //stickiness - toggles the class to fixed/not-fixed to trigger CSS
        var my_circle = d3.select(this).selectAll('circle')
        //console.log(my_circle);
        var classes = my_circle.attr('class').split(' ');
        if (my_circle.attr('class') == "fixed " + classes[1]) {
          my_circle.attr("class", "not-fixed " + classes[1])
        } else {
          my_circle.attr("class", "fixed " + classes[1])
        }
        //}
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
  var session = driver.session();
  var results = session.run(statement, { limit: 200 });
  session.close();

  return results;
}

function getGraph(records) {

  var nodes = [], edges = [], i = 0, userClasses = [];



  /*  if (typeof records !== 'undefined' && records.length > 0) {
       keys = records[0].keys;
   } */

  records.forEach(res => {

    //nodes.push({title: res.get(keys[0]), label: keys[0]});

    var info = {
      Anzahl_Installationen: res.get('info').properties.Anzahl_Installationen,
      Beschreibung: res.get('info').properties.Beschreibung,
      Code: res.get('info').properties.Code,
      Eingesetzt_seit: res.get('info').properties.Eingesetzt_seit,
      Investitionsgroesse: res.get('info').properties.Investitionsgroesse,
      Name: res.get('info').properties.Name,
      Subsysteme: res.get('info').properties.Subsysteme,
      type: "Informationssystem"
    };

    var source = _.findIndex(nodes, info);

    if (source == -1) {
      nodes.push(info);
      source = i;
      i++;
    }

    var orga = {
      Name: res.get('orga').properties.Name,
      Uebergeordnete_Einheit: res.get('orga').properties.Uebergeordnete_Einheit,
      type: "Organisationseinheit"
    };

    var target = _.findIndex(nodes, orga);

    if (target == -1) {
      nodes.push(orga);
      target = i;
      i++;
    }

    var type = "Organisationseinheit";

    var test = _.findIndex(edges, { source, target, type });
    if (test == -1) {
      edges.push({ source, target, type });
    }





    var pers = {
      Name: res.get('pers').properties.Name,
      type: "Person"
    };

    target = _.findIndex(nodes, pers);

    if (target == -1) {
      nodes.push(pers);
      target = i;
      i++;
    }

    type = "Person";

    test = _.findIndex(edges, { source, target, type });
    if (test == -1) {
      edges.push({ source, target, type });
    }




    var tech = {
      Beschreibung: res.get('tech').properties.Beschreibung,
      EndOfLife: res.get('tech').properties.EndOfLife,
      Name: res.get('tech').properties.Name,
      type: "Technologie"
    };

    target = _.findIndex(nodes, tech);

    if (target == -1) {
      nodes.push(tech);
      target = i;
      i++;
    }

    type = "Technologie";

    test = _.findIndex(edges, { source, target, type });
    if (test == -1) {
      edges.push({ source, target, type });
    }



    var anzaClass = res.get('anza').properties.Klasse;


    /* target = _.findIndex(nodes, anza);

    if(target == -1) {
      nodes.push(anza);
      target = i;
      i++;
    } */

    type = "Anzahl_Anwender";

    test = _.findIndex(userClasses, { source, anzaClass });
    if (test == -1) {
      userClasses.push({ source, anzaClass });
    }


    //edges.push({source, target});

    /*res.get('tech').forEach(o => {
      var value = o.properties.Name;
    })
  
    res.get('orga').forEach(v => {

      var value = {title: v, label: keys[1]};
      var source = _.findIndex(nodes, value);

      if (source == -1) {
        nodes.push(value);
        source = i;
        i++;
      }

      edges.push({source, target})
    })*/

  });
  return { nodes, edges, userClasses };

}

function getData() {
  return new Promise(resolve => {
    //parseCypherToDB('MATCH (m:Movie)<-[:ACTED_IN]-(a:Person) RETURN m.title AS movie, collect(a.name) AS actor LIMIT {limit}').then(results => {
    //parseCypherToDB('MATCH (i:Informationssystem)<-[verantwortlich]-(p:Person) return i,p').then(results => {
    parseCypherToDB('MATCH (info:Informationssystem)-[:Eingesetzt_In]->(orga:Organisationseinheit), (info)<-[:verantwortlich]-(pers:Person), (info)-[:verwendet]->(tech:Technologie), (info)-[:besitzt]->(anza:Anzahl_Anwender) return info,orga,pers,tech,anza').then(results => {
      var g = getGraph(results.records);
      resolve(g);
    });

  });
}



