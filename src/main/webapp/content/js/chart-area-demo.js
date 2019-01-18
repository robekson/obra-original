// Set new default font family and font color to mimic Bootstrap's default styling

function buildLineChart() {
    Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
    Chart.defaults.global.defaultFontColor = '#292b2c';

    // Area Chart Example
    var ctx = document.getElementById("myAreaChart");

    if(ctx!=null){
     document.getElementById("showChart").value=null;
     var myLineChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: ["Nov/2017", "Dez/2017", "Jan/2018", "Fev/2018", "Mar/2018", "Abr/2018", "Mai/2018", "Jun/2018", "Agos/2018", "Set/2018", "Out/2018", "Nov/2018", "Dez/2018"],
        datasets: [{
          label: "Gastos",
          lineTension: 0.3,
          backgroundColor: "rgba(2,117,216,0.2)",
          borderColor: "rgba(2,117,216,1)",
          pointRadius: 5,
          pointBackgroundColor: "rgba(2,117,216,1)",
          pointBorderColor: "rgba(255,255,255,0.8)",
          pointHoverRadius: 5,
          pointHoverBackgroundColor: "rgba(2,117,216,1)",
          pointHitRadius: 50,
          pointBorderWidth: 2,
          data: [10000, 30162, 26263, 18394, 18287, 28682, 31274, 33259, 25849, 24159, 32651, 31984, 38451],
        }],
      },
      options: {
        scales: {
          xAxes: [{
            time: {
              unit: 'date'
            },
            gridLines: {
              display: false
            },
            ticks: {
              maxTicksLimit: 7
            }
          }],
          yAxes: [{
            ticks: {
              min: 0,
              max: 40000,
              maxTicksLimit: 5
            },
            gridLines: {
              color: "rgba(0, 0, 0, .125)",
            }
          }],
        },
        legend: {
          display: false
        }
      }
    });
  }

}
setInterval(function () {
    var chart = document.getElementById("showChart");
   // alert(chart);
    if(chart!=null && chart.value.length > 0) buildLineChart();

  }, 1000);
