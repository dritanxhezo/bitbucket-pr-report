function ready(fn) {
  if (document.attachEvent ? document.readyState === "complete" : document.readyState !== "loading"){
    fn();
  } else {
    document.addEventListener('DOMContentLoaded', fn);
  }
}

function ajax(url, callback) {
  var request = new XMLHttpRequest();
  request.open('GET', url, true);

  request.onload = function() {
    if (this.status >= 200 && this.status < 400) {
      // Success!
      var data = JSON.parse(this.response);
      callback(null, data);
    } else {
      // We reached our target server, but it returned an error
      callback('error');
    }
  };

  request.onerror = function() {
    // There was a connection error of some sort
    callback('error');
  };

  request.send();
}

function plotChart(options) {
  Highcharts.chart('container', {
      chart: {
          plotBackgroundColor: null,
          plotBorderWidth: null,
          plotShadow: false,
          type: 'pie'
      },
      title: {
          text: options.title
      },
      tooltip: {
          pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
      },
      plotOptions: {
          pie: {
              allowPointSelect: true,
              cursor: 'pointer',
              dataLabels: {
                  enabled: true,
                  format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                  style: {
                      color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                  }
              }
          }
      },
      series: [{
          name: options.seriesName,
          colorByPoint: true,
          data: options.data
      }]
  });
}

function deploymentsPerRepository() {
  ajax('/stats/deployments-per-repository', function(err, data) {
    if (data) {
      plotChart({
        title: 'Deployments per repository',
        seriesName: 'Deployments',
        data: data.map(x => ({
           name: x.slug,
           y: x.count
        }))
      });
    }
  });
}

function buildsPerUser() {
  ajax('/stats/builds-per-user', function(err, data) {
    if (data) {
      plotChart({
        title: 'Builds per user',
        seriesName: 'Builds',
        data: data.map(x => ({
           name: x.username,
           y: x.count
        }))
      });
    }
  });
}

ready(function() {
  document.getElementById('builds-per-user').addEventListener('click', buildsPerUser);
  document.getElementById('deployments-per-repository').addEventListener('click', deploymentsPerRepository);
});
