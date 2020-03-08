

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);


function structureData(){
 const data = new google.visualization.DataTable();
  data.addColumn('string', 'Interest');
  data.addColumn('number', 'Count');
        data.addRows([
          ['Fitness', 15],
          ['Marketing', 5],
          ['Programming', 17],
          ['Entrepenuership',15],
          ['Stock Market Investing',10],
          ['Anime',5]
        ]);

    return data;
}

function createOptions(){
  const options = {
    'title': 'My interests',
    'width':800,
    'height':600
  };

  return options;
}

/** Creates a chart and adds it to the page. */
function drawChart() {
  data = structureData();
  options = createOptions();
  const chart = new google.visualization.PieChart(
      document.getElementById('chart-container'));
  chart.draw(data, options);
}