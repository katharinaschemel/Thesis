



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


//defining the width and height of the svg
var width = window.innerWidth;// default width
var height = window.innerHeight;

getData().then(g =>{
  nodes = g.nodes; 
  edges = g.edges;

  
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
            .attr("background-color","yellow")
            .call(myChart);



  function familyChart() {


    var nodes = [], links = [] // default height

    function my(svg) {

      var family_radius = 15;

      var repelForce = d3.forceManyBody().strength(-3000).distanceMax(450)
                       .distanceMin(85);

      var simulation = d3.forceSimulation()
                  //     .alphaDecay(0.04)
                  //     .velocityDecay(0.4)
                       .force("center", d3.forceCenter(width / 2, height / 2))
                       .force("xAxis",d3.forceX(width/2).strength(0.4))
                       .force("yAxis",d3.forceY(height/2).strength(0.6))
                       .force("repelForce",repelForce)
                       .force("link", d3.forceLink()/*.id(function(d) { return d.id }).distance(dist).strength(1.5)*/)
                       .force("collide",d3.forceCollide().radius(function(d) { return d.r * 20; }).iterations(10).strength(1));

      function dist(d){
      //used by link force
      return 100

      }


      var links = svg.selectAll("foo")
        .data(edges)
        .enter()
        .append("line")
        .attr("stroke-width", "4px")
        .attr("stroke-dasharray", "6,6")
      .attr("stroke", "gold");

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

      var circles = node.append("circle")
                      .attr("class","circle")
                      .attr("r", family_radius)
                      .attr("fill", function(d) {if(d.label == "movie"){ return "blue"} else{ return "red"}})
                      .attr("stroke", "gold")
                      .attr("stroke-width","2px")
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
      simulation.on("tick", function() {

        links.attr("x1", function(d) {return d.source.x;})
             .attr("y1", function(d) {return d.source.y;})
             .attr("x2", function(d) {return d.target.x;})
             .attr("y2", function(d) {return d.target.y;})

        node.attr("transform", function(d){ return "translate(" + d.x + "," + d.y + ")"})
      });

      function dragstarted(d) {

        if (!d3.event.active) simulation.alphaTarget(0.3).restart();
          d.fx = d.x;
          d.fy = d.y;
        if(d.type == 'family'){
          //stickiness - toggles the class to fixed/not-fixed to trigger CSS
          var my_circle = d3.select(this).selectAll('circle')
          if(my_circle.attr('class') == 'fixed'){
            my_circle.attr("class","not-fixed")
          }else{
            my_circle.attr("class","fixed")
          }
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
         if(my_circle.attr('class') == 'not-fixed'){
           d.fx = null;
           d.fy = null;
         }
  
      }


    }

    my.width = function(value) {
      if (!arguments.length) return width;
      width = value;
      return my;
    };

    my.nodes = function(value) {
      if (!arguments.length) return nodes;
      nodes = value;
      return my;
    };

    my.links = function(value) {
      if (!arguments.length) return links;
      links = value;
      return my;
    };

    my.height = function(value) {
      if (!arguments.length) return height;
      height = value;
      return my;
    };

    return my; 
    
  }


});






function parseCypherToDB(statement) {
  var session = driver.session();
  var results = session.run(statement, {limit: 200});
  session.close();

  return results;
}

function getGraph(records) {

  var nodes = [], edges = [], i = 0, keys = [];

 
    
    if (typeof records !== 'undefined' && records.length > 0) {
        keys = records[0].keys;
    }
    
    records.forEach(res => {

      nodes.push({title: res.get(keys[0]), label: keys[0]});

      var target = i;
      i++;

    
      res.get(keys[1]).forEach(v => {

        var value = {title: v, label: keys[1]};
        var source = _.findIndex(nodes, value);

        if (source == -1) {
          nodes.push(value);
          source = i;
          i++;
        }

        edges.push({source, target})
      })
      
    });
  return {nodes, edges};
  
}

function getData() {
  return new Promise(resolve => {
    parseCypherToDB('MATCH (m:Movie)<-[:ACTED_IN]-(a:Person) RETURN m.title AS movie, collect(a.name) AS actor LIMIT {limit}').then(results => {
      var g = getGraph(results.records); 
      resolve(g);  
    });
  
  });
}



