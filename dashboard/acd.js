//
// (C) Vodia Networks 2020
//
// This file is property of Vodia Networks Inc. All rights reserved. 
// For more information mail Vodia Networks Inc., info@vodia.com.
//
'use strict';
document.addEventListener('pbx.show.aui', function(e) {
    let ampm = new Date(3600000).toLocaleTimeString().split(' ').pop();
    let hour12 = ampm == 'AM' || ampm == 'PM';
    let mmdd = new Date(3600000).toLocaleDateString().split('/').length > 2;
    let agents = {};
    let acdinfo = {};
    let acdCrdHtml;
    let selectedAcd;
    let agentData;
    let initialData = {
        calls: 0,
        incalls: 0,
        outcalls: 0,
        missed: 0,
        durationivr: 0,
        durationring: 0,
        durationtalk: 0,
        durationhold: 0,
        durationidle: 0,
        avgdurationring: 0,
        avgdurationivr: 0,
        rw: 0,
        rr: 0,
        ra: 0,
        hc: 0,
        hw: 0,
        hr: 0,
        ui: 0,
        ai: 0,
        mc: 0,
        soap: 0,
        other: 0,
        calllist: []
    };
    let managers = [];
    let isManager = false;
    let pieChart, lineChart, lineChartType, lineChartDate, acdId;
    let date = getDate();
    let lineChartData = [];
    let acdCdrData = [];
    let acdSettingsData = {};
    let currentWallboardData, currentWallboardEvt;
    let wallboard = document.getElementById('acd-content');
    wallboard.innerHTML = '';
    async function loadAcd() {
        pbx.showAcd();
        hideCdr();
        var url = pbx.getServer() + '/rest/user/' + pbx.getUnDn() + '/wallboard';
        fetch(url).then(function(res) {
            return res.json()
        }).then(async function(data) {
            if (isEmpty(data)) {
                data = initialData
            }
            var left = document.getElementById('acd-controls');
            left.innerHTML = '';
            left.style.display = '';
            var ext = '';
            pbxView.acds = {};
            for (var i = 0; i < data.length; i++) {
                let button = document.createElement('button');
                let joinAcd = document.createElement('button');
                let leaveAcd = document.createElement('button');
                let closeAcdMenu = document.createElement('div');
                let closeIcon = document.createElement('i');
                let wrap = document.createElement('div');
                let loginStatus = document.createElement('div');
                let acdId = data[i].name;
                let wallData = await loadWallboardAcd(acdId);
                let agents = wallData.agents;
                let managers = wallData.acds[acdId].managers;
                let isManager = managers.includes(pbx.getUn());
                var results = agents.filter(function(entry) {
                    return entry.account === pbx.getUn()
                });
                if (results.length > 0) {
                    if (results[0].login) {
                        loginStatus.classList.add('in')
                    } else {
                        loginStatus.classList.add('out')
                    }
                    wrap.classList.add('pbx-acd-remove-current-agent')
                } else {
                    wrap.classList.add('pbx-acd-add-current-agent')
                }
                ext = data[i].display;
                button.innerText = data[i].name + ' (' + data[i].display + ')';
                button.id = 'pbx-acd-button-' + data[i].name;
                button.classList.add('acd-selector');
                button.appendChild(loginStatus);
                if (isManager) {
                    acdSettings(acdId);
                    button.addEventListener('click', loadWallboard.bind(this, data[i]));
                    button.addEventListener('contextmenu', function(el) {
                        el.preventDefault();
                        let parent = el.target.parentElement;
                        parent.classList.toggle('show-menu')
                    });
                    closeAcdMenu.classList.add('pbx-acd-join-leave-close');
                    closeIcon.classList.add('material-icons');
                    closeIcon.innerText = 'close';
                    joinAcd.classList.add('pbx-acd-join');
                    leaveAcd.classList.add('pbx-acd-leave');
                    joinAcd.innerText = 'Join';
                    leaveAcd.innerText = 'Leave';
                    joinAcd.addEventListener('click', acdJoin.bind(this, data[i].name));
                    leaveAcd.addEventListener('click', acdLeave.bind(this, data[i].name));
                    closeAcdMenu.appendChild(closeIcon);
                    wrap.appendChild(button);
                    wrap.appendChild(joinAcd);
                    wrap.appendChild(leaveAcd);
                    wrap.appendChild(closeAcdMenu)
                } else {
                    wrap.appendChild(button)
                }
                left.appendChild(wrap);
                if (i == 0) {
                    loadWallboard(data[i], button)
                }
                pbxView.acds[ext] = data[i]
            }
        })
    }
    async function loadWallboardAcd(id) {
        let url = pbx.getServer() + '/rest/user/' + pbx.getUnDn() + '/wallboard/' + encodeURIComponent(id);
        let res = await fetch(url);
        let wallData = await res.json();
        return wallData
    }
    function loadWallboard(data, evt) {
        currentWallboardData = data;
        currentWallboardEvt = evt;
        acdId = data.name;
        var select = document.getElementById('acd-content').getElementsByClassName('preset-date')[0];
        if (select) {
            select.value = 0
        }
        var acdControls = document.getElementById('acd-controls').childNodes;
        for (var i = 0; i < acdControls.length; i++) {
            acdControls[i].getElementsByTagName('button')[0].classList.remove('active')
        }
        if (evt.srcElement) {
            evt.srcElement.classList.add('active')
        } else {
            evt.classList.add('active')
        }
        var acdIdInt = Number(data.name);
        selectedAcd = acdId;
        var url = pbx.getServer() + '/rest/user/' + pbx.getUnDn() + '/wallboard/' + encodeURIComponent(acdId);
        fetchWallboard(url, acdId);
        fetchCdr();
        document.addEventListener('pbx.notify.state', function(e) {
            if (e.detail.type == 'extensions') {}
        });
        let liveCalls = document.getElementById('live-calls-acd');
        liveCalls.innerText = 'ACD: ' + acdId
    }
    document.addEventListener('pbx.livecalls', function(e) {
        getAcdLiveCalls()
    });
    function getAcdLiveCalls() {
        if (document.body.classList.contains('acd-window')) {
            let liveCalls = document.getElementById('integrations-block-view');
            liveCalls.childNodes.forEach(function(item, index) {
                if (index >= 0) {
                    try {
                        if (item.classList.contains('pbx-live-call-acd-' + acdId)) {
                            item.classList.remove('hide-pbx-live-call')
                        } else {
                            item.classList.add('hide-pbx-live-call')
                        }
                    } catch (e) {
                        console.log(e)
                    }
                }
            })
        }
    }
    async function fetchCdr() {
        let acdId = selectedAcd;
        let url = pbx.getServer() + '/rest/user/' + pbx.getUnDn() + '/acdcdr?acd=' + encodeURIComponent(acdId) + '&page=1';
        let res = await fetch(url);
        let data = await res.json();
        return data
    }
    async function showCdr(argument) {
        let data = await fetchCdr();
        let doc = await fetchCdrHtml();
        let container = document.getElementById('acd-cdr-list');
        container.innerHTML = '';
        data.forEach(function(item) {
            let c = doc.cloneNode(true);
            let from = pbx.prettify_from_to_spec(item.from);
            let timestamp = new Date(item.start * 1000);
            item.reason && c.getElementsByClassName('pbx-list-row')[0].classList.add(item.reason);
            if (item.reason == 'hc' || item.reason == '') {
                c.getElementsByClassName('pbx-cdr-inbound')[0].style.display = 'block';
                c.getElementsByClassName('pbx-cdr-ring')[0].innerText = pbx.miliseconds_to_hms(item.ring);
                c.getElementsByClassName('pbx-cdr-talk')[0].innerText = pbx.miliseconds_to_hms(item.talk)
            } else if (item.reason == 'hr') {
                c.getElementsByClassName('pbx-cdr-missed')[0].style.display = 'block';
                c.getElementsByClassName('pbx-cdr-ring')[0].innerText = pbx.miliseconds_to_hms(item.ring)
            } else if (item.reason == 'hw') {
                c.getElementsByClassName('pbx-cdr-abandoned')[0].style.display = 'block'
            }
            c.getElementsByClassName('pbx-cdr-ivr')[0].innerText = pbx.miliseconds_to_hms(item.ivr);
            c.getElementsByClassName('pbx-cdr-start')[0].appendChild(document.createTextNode(timestamp.toLocaleString()));
            c.getElementsByClassName('pbx-cdr-name')[0].innerText = from[1].trim().replace(/^"(.*)"$/, '$1');
            c.getElementsByClassName('pbx-cdr-number')[0].innerText = pbx.localNumber(from[2]);
            container.appendChild(c)
        });
        document.getElementById('acd-window').classList.add('acd-cdr-view')
    }
    async function fetchCdrHtml() {
        if (!acdCrdHtml) {
            let doc = await pbx.html('usr_acd_cdr.htm');
            acdCrdHtml = doc
        }
        return acdCrdHtml
    }
    function hideCdr() {
        document.getElementById('acd-window').classList.remove('acd-cdr-view')
    }
    async function fetchWallboard(url, acdId) {
        getAcdLiveCalls();
        var baseAcdUrl = pbx.getServer() + '/rest/user/' + pbx.getUnDn() + '/acdinfo?group=' + encodeURIComponent(acdId) + '&date=';
        var acdUrl = baseAcdUrl + encodeURIComponent(date.today);
        fetch(url).then(function(res) {
            return res.json()
        }).then(function(data) {
            if (isEmpty(data)) {
                data = initialData
            }
            acdinfo = data;
            managers = data.acds[acdId].managers;
            isManager = managers.includes(sessionStorage.getItem('user'));
            let agents = data.agents;
            var isAgent = agents.filter(function(entry) {
                return entry.account === pbx.getUn()
            });
            var isManager = data.acds[selectedAcd].managers.filter(function(entry) {
                return entry === pbx.getUn()
            });
            pbx.html('usr_acd.htm').then(async function(doc) {
                wallboard.innerHTML = '';
                var c = doc;
                var e = data.acds[acdId];
                var ext = '';
                var devFeature = c.getElementsByClassName('grey-out')[0];
                devFeature.style.display = '';
                var dateSelector = c.getElementsByClassName('preset-date')[0];
                dateSelector.onchange = dateOptions.bind(dateSelector, c, baseAcdUrl, acdUrl, acdId, data, devFeature);
                getAcdData(baseAcdUrl, date.today, c);
                getAcdAgentsData(acdId, data, date.today, c);
                fetchMultipleUrl(baseAcdUrl, date.today_hours).then(function(e) {
                    drawLineChart(e, 'line', 'hours')
                });
                c.getElementsByClassName('chart-2-line')[0].addEventListener('click', function() {
                    drawLineChart(lineChartData, 'line', lineChartDate)
                });
                c.getElementsByClassName('chart-2-bar')[0].addEventListener('click', function() {
                    drawLineChart(lineChartData, 'bar', lineChartDate)
                });
                c.getElementsByClassName('total-calls-tile')[0].addEventListener('click', showCdr);
                c.getElementsByClassName('in-calls-tile')[0].addEventListener('click', showCdr);
                c.getElementsByClassName('missed-calls-tile')[0].addEventListener('click', showCdr);
                wallboard.append(c)
            })
        })
    }
    function dateOptions(c, baseAcdUrl, acdUrl, acdId, data, devFeature) {
        switch (this.value) {
        case '0':
            getAcdData(baseAcdUrl, date.today, c);
            getAcdAgentsData(acdId, data, date.today, c);
            fetchMultipleUrl(baseAcdUrl, date.today_hours).then(function(e) {
                drawLineChart(e, 'line', 'hours')
            });
            devFeature.style.display = 'none';
            break;
        case '1':
            getAcdData(baseAcdUrl, date.yesterday, c);
            getAcdAgentsData(acdId, data, date.yesterday, c);
            fetchMultipleUrl(baseAcdUrl, date.yesterday_hours).then(function(e) {
                drawLineChart(e, 'line', 'hours')
            });
            devFeature.style.display = 'none';
            break;
        case '2':
            getCombinedAcdAgentsData(acdId, data, date.this_week_days, c);
            fetchMultipleUrl(baseAcdUrl, date.this_week_days).then(function(e) {
                showAcdData(statsSum(e), c);
                drawLineChart(e, 'bar')
            });
            devFeature.style.display = 'none';
            break;
        case '3':
            getCombinedAcdAgentsData(acdId, data, date.last_week_days, c);
            fetchMultipleUrl(baseAcdUrl, date.last_week_days).then(function(e) {
                showAcdData(statsSum(e), c);
                drawLineChart(e, 'line')
            });
            devFeature.style.display = 'none';
            break;
        case '4':
            getCombinedAcdAgentsData(acdId, data, date.this_month, c);
            fetchMultipleUrl(baseAcdUrl, date.this_month).then(function(e) {
                showAcdData(statsSum(e), c);
                drawLineChart(e, 'line')
            });
            devFeature.style.display = 'none';
            break;
        case '5':
            getCombinedAcdAgentsData(acdId, data, date.last_month, c);
            fetchMultipleUrl(baseAcdUrl, date.last_month).then(function(e) {
                showAcdData(statsSum(e), c);
                drawLineChart(e, 'line')
            });
            devFeature.style.display = 'none';
            break;
        }
    }
    async function fetchAgent(userId, data) {
        var container = document.getElementById('acd-agent');
        container.style.display = '';
        var left = document.getElementById('acd-agent-content');
        left.innerHTML = '';
        var controls = document.getElementById('acd-agent-controls');
        var acd = document.getElementById('acd-content');
        acd.style.display = 'none';
        controls.getElementsByClassName('arrow-back')[0].addEventListener('click', function() {
            container.style.display = 'none';
            acd.style.display = ''
        });
        document.getElementById('acd-agent-name').innerText = data.name;
        document.getElementById('acd-agent-number').innerText = data.account;
        document.getElementById('acd-agent-img').src = pbx.getServer() + '/rest/domain/' + pbx.getDn() + '/account/' + pbx.getUn() + '/image?category=picture&resolution=150x150&ext=' + encodeURIComponent(data.account);
        pbx.html('usr_acd_agent_stats.htm').then(async function(doc) {
            let c = doc.cloneNode(true);
            let startTime = new Date(data.work.begin * 1000);
            let endTime = new Date(data.work.end * 1000);
            let startPoint, endPoint;
            startPoint = getTimeInPercent(startTime);
            if (startTime.getDate() == endTime.getDate()) {
                endPoint = getTimeInPercent(endTime)
            } else {
                c.getElementsByClassName('acd-agent-work-duration')[0].classList.add('day-2');
                endPoint = 100;
                let secondEndPoint = getTimeInPercent(endTime);
                c.getElementsByClassName('acd-agent-work-frame')[1].style = 'left: ' + 0 + '%; width: ' + secondEndPoint + '%'
            }
            c.getElementsByClassName('acd-agent-work-frame')[0].style = 'left: ' + startPoint + '%; width: ' + (endPoint - startPoint) + '%';
            if (data.work.segments.length > 0) {
                let breakSection = c.getElementsByClassName('acd-agent-work-breaks')[0];
                let breaksData = data.work.segments;
                breaksData.forEach(function(item, index) {
                    let start = new Date(item[0] * 1000);
                    let end = new Date(item[1] * 1000);
                    let breakStart = getTimeInPercent(start);
                    let breakEnd = getTimeInPercent(end);
                    let breakDiv = document.createElement('div');
                    let breakTooltip = document.createElement('span');
                    breakDiv.classList.add('acd-agent-work-break-' + index, 'tooltip');
                    breakDiv.style = 'left: ' + breakStart + '%; width: ' + (breakEnd - breakStart) + '%';
                    breakTooltip.classList.add('tooltiptext', 'tooltip-top');
                    let sm = start.getMinutes();
                    let em = end.getMinutes();
                    if (sm < 10)
                        sm = '0' + sm;
                    if (em < 10)
                        em = '0' + sm;
                    breakTooltip.innerText = 'From: ' + start.getHours() + ':' + sm + '; To: ' + end.getHours() + ':' + em;
                    breakDiv.appendChild(breakTooltip);
                    breakSection.appendChild(breakDiv)
                })
            }
            let cdrData = await fetchCdr();
            let cdrTemplate = await fetchCdrHtml();
            let callsList = c.getElementsByClassName('acd-agent-calls')[0];
            callsList.innerHTML = '';
            cdrData.forEach(function(item, index) {
                if (pbx.prettify_from_to_spec(item.from)[2] === data.account || pbx.prettify_from_to_spec(item.to)[2] === data.account || item.extension === data.account) {
                    let c = cdrTemplate.cloneNode(true);
                    let from = pbx.prettify_from_to_spec(item.from);
                    let timestamp = new Date(item.start * 1000);
                    item.reason && c.getElementsByClassName('pbx-list-row')[0].classList.add(item.reason);
                    if (item.reason == 'hc' || item.reason == '') {
                        c.getElementsByClassName('pbx-cdr-inbound')[0].style.display = 'block';
                        c.getElementsByClassName('pbx-cdr-ring')[0].innerText = pbx.miliseconds_to_hms(item.ring);
                        c.getElementsByClassName('pbx-cdr-talk')[0].innerText = pbx.miliseconds_to_hms(item.talk)
                    } else if (item.reason == 'hr') {
                        c.getElementsByClassName('pbx-cdr-missed')[0].style.display = 'block';
                        c.getElementsByClassName('pbx-cdr-ring')[0].innerText = pbx.miliseconds_to_hms(item.ring)
                    } else if (item.reason == 'hw') {
                        c.getElementsByClassName('pbx-cdr-abandoned')[0].style.display = 'block'
                    }
                    c.getElementsByClassName('pbx-cdr-ivr')[0].innerText = pbx.miliseconds_to_hms(item.ivr);
                    c.getElementsByClassName('pbx-cdr-start')[0].appendChild(document.createTextNode(timestamp.toLocaleString()));
                    c.getElementsByClassName('pbx-cdr-name')[0].innerText = from[1].trim().replace(/^"(.*)"$/, '$1');
                    c.getElementsByClassName('pbx-cdr-number')[0].innerText = pbx.localNumber(from[2]);
                    callsList.appendChild(c)
                }
            });
            left.append(c)
        })
    }
    function getTimeInPercent(data) {
        let position = (data.getHours() * 3600 + data.getMinutes() * 60 + data.getSeconds()) / 864;
        return position
    }
    function getAcdData(acdUrl, date, c) {
        fetch(acdUrl + date).then(function(res) {
            return res.json()
        }).then(function(data) {
            showAcdData(data, c)
        })
    }
    function showAcdData(data, c) {
        if (isEmpty(data)) {
            data = initialData;
            var callAbandon = 0 + '%';
            var callTransfer = 0 + '%';
            var callAnswerSpeed = 0
        } else {
            var callAbandon = ((data.hr + data.hw) / data.calls * 100).toFixed(1) + '%';
            var callTransfer = ((data.ra + data.rr + data.rw) / data.calls * 100).toFixed(1) + '%';
            var callAnswerSpeed = (data.avgdurationring + data.avgdurationivr).toFixed(0)
        }
        c.getElementsByClassName('acd-calls-total')[0].innerText = data.calls;
        c.getElementsByClassName('acd-calls-in')[0].innerText = data.incalls;
        c.getElementsByClassName('acd-calls-out')[0].innerText = data.outcalls;
        c.getElementsByClassName('acd-calls-missed')[0].innerText = data.missed;
        c.getElementsByClassName('acd-calls-abandon')[0].innerText = callAbandon;
        c.getElementsByClassName('acd-calls-transfer')[0].innerText = callTransfer;
        c.getElementsByClassName('acd-calls-soa')[0].innerText = pbx.miliseconds_to_hms(callAnswerSpeed);
        drawPieChart(data)
    }
    function getAcdAgentsData(acdId, data, date, c) {
        var agentList = c.getElementsByClassName('users-list')[0];
        agentList.innerHTML = '';
        pbx.html('usr_acd_agent.htm').then(function(doc) {
            data.agents.forEach(function(item) {
                var agentUrl = pbx.getServer() + '/rest/user/' + pbx.getUnDn() + '/acdinfo?group=' + encodeURIComponent(acdId) + '&agent=' + encodeURIComponent(item.account) + '&date=' + encodeURIComponent(date);
                fetch(agentUrl).then(function(res) {
                    return res.json()
                }).then(function(data) {
                    showAcdAgentsData(data, doc, item, c)
                })
            })
        })
    }
    function getCombinedAcdAgentsData(acdId, data, date, c) {
        var agentList = c.getElementsByClassName('users-list')[0];
        agentList.innerHTML = '';
        pbx.html('usr_acd_agent.htm').then(function(doc) {
            data.agents.forEach(function(item) {
                var agentUrl = pbx.getServer() + '/rest/user/' + pbx.getUnDn() + '/acdinfo?group=' + encodeURIComponent(acdId) + '&agent=' + encodeURIComponent(item.account) + '&date=';
                fetchMultipleUrl(agentUrl, date).then(function(e) {
                    showAcdAgentsData(statsSum(e), doc, item, c)
                })
            })
        })
    }
    function showAcdAgentsData(data, doc, item, c) {
        var agentList = c.getElementsByClassName('users-list')[0];
        if (Object.keys(data).length == 0) {
            data.calls = 0;
            var occupancy = 0;
            data.incalls = 0
        } else {
            var frame = (data.durationidle + data.durationhold + data.durationtalk) / 100;
            var occupancy = (data.durationhold + data.durationtalk) / frame
        }
        var c = doc.cloneNode(true);
        var loggedin = true;
        for (var i = 0; loggedin && i < acdinfo.agents.length; i++) {
            var agent = acdinfo.agents[i];
            if (agent.account != item.account)
                continue;
            if (!agent.login)
                loggedin = false
        }
        c.classList.add(loggedin ? 'logged-in' : 'logged-out');
        var action = 'true';
        c.getElementsByClassName('pbx-ext-name')[0].appendChild(document.createTextNode(item.name));
        c.getElementsByClassName('pbx-ext-number')[0].appendChild(document.createTextNode(item.account));
        var url = pbx.getServer() + '/rest/domain/' + pbx.getDn() + '/account/' + pbx.getUn() + '/image?category=picture&resolution=150x150&ext=' + encodeURIComponent(item.account);
        c.getElementsByClassName('pbx-ext-image')[0].src = url;
        if (data.calllist) {
            data.calllist.forEach(function(e, id) {
                if (e.duration > 0) {
                    var call = document.createElement('div');
                    call.classList.add('pbx-ext-call-detail', 'y-center');
                    call.style.left = e.duration / 36000 + '%';
                    c.getElementsByClassName('pbx-acd-call-list')[0].appendChild(call)
                }
            })
        }
        c.getElementsByClassName('pbx-ext-call-count')[0].appendChild(document.createTextNode(data.incalls));
        c.getElementsByClassName('pbx-ext-info')[0].addEventListener('click', fetchAgent.bind(this, item.account, item));
        c.getElementsByClassName('occupancy')[0].style.width = occupancy + '%';
        if (isManager || item.account === pbx.getUn()) {
            c.getElementsByClassName('pbx-acd-agent-log-in-out')[0].addEventListener('click', acdLogin.bind(this, item.account, action));
            c.getElementsByClassName('pbx-ext-image')[0].addEventListener('click', acdLeave.bind(this, acdId))
        } else {
            c.getElementsByClassName('pbx-acd-agent-log-in-out')[0].remove()
        }
        agentList.appendChild(c);
        agents[item.account] = c
    }
    function drawPieChart(data) {
        if (pieChart) {
            pieChart.clear();
            pieChart.destroy()
        }
        var chart_1 = document.getElementById('chart-1');
        var pieDataset = [(data.durationhold / 1000).toFixed(1), (data.durationidle / 1000).toFixed(1), (data.durationivr / 1000).toFixed(1), (data.durationring / 1000).toFixed(1), (data.durationtalk / 1000).toFixed(1)];
        var pieData = {
            labels: ['Hold', 'Idle', 'IVR', 'Ring', 'Talk'],
            datasets: [{
                backgroundColor: ['#F8B559', '#F76255', '#157BFB', '#27C676', '#27a1b0'],
                data: pieDataset
            }]
        };
        var pieOptions = {
            title: {
                display: false,
                text: 'Duration'
            },
            legend: {
                display: false
            }
        };
        pieChart = new Chart(chart_1,{
            type: 'doughnut',
            data: pieData,
            options: pieOptions
        })
    }
    function drawLineChart(data, type, time) {
        lineChartData = data;
        lineChartType = type;
        lineChartDate = time;
        if (lineChart) {
            lineChart.clear();
            lineChart.destroy()
        }
        var labels = [];
        var labelDate;
        var calls = [];
        var callsIn = [];
        var callsOut = [];
        var callsMissed = [];
        var soa = [];
        var chartData;
        var chartOptions, chartData;
        if (!isEmpty(data)) {
            if (typeof data.length == 'undefined') {} else {
                data.forEach(function(e) {
                    if (e.data.avgdurationring + e.data.avgdurationivr == 0) {
                        var callAnswerSpeed = 0
                    } else {
                        var callAnswerSpeed = ((e.data.avgdurationring + e.data.avgdurationivr) / 1000).toFixed(0)
                    }
                    if (time == 'hours') {
                        var hour = Number(e.date.slice(0, 2));
                        if (hour12) {
                            if (hour == 0)
                                labelDate = '12 AM';
                            else if (hour == 12)
                                labelDate = '12 PM';
                            else if (hour > 11)
                                labelDate = hour - 12 + ' PM';
                            else
                                labelDate = hour + ' AM'
                        } else {
                            labelDate = String(hour)
                        }
                    } else if (mmdd) {
                        labelDate = e.date.substr(2, 2) + '/' + e.date.substr(0, 2)
                    } else {
                        labelDate = e.date.substr(0, 2) + '.' + e.date.substr(2, 2)
                    }
                    labels.push(labelDate);
                    calls.push(e.data.calls);
                    callsIn.push(e.data.incalls);
                    callsOut.push(e.data.outcalls);
                    callsMissed.push(e.data.missed);
                    soa.push(callAnswerSpeed)
                })
            }
        }
        var chart_2 = document.getElementById('chart-2');
        if (type == 'line') {
            chartData = {
                labels: labels,
                datasets: [{
                    data: soa,
                    label: 'Avg speed of answer',
                    borderColor: '#157BFB',
                    fill: false
                }]
            };
            chartOptions = {
                title: {
                    display: false,
                    text: 'Call stats'
                },
                legend: {
                    display: false
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            min: 0,
                            callback: function(value, index, values) {
                                if (Math.floor(value) === value) {
                                    return value
                                }
                            }
                        }
                    }]
                }
            }
        } else {
            chartData = {
                labels: labels,
                datasets: [{
                    label: 'In',
                    data: callsIn,
                    backgroundColor: '#157bfb'
                }, {
                    label: 'Out',
                    data: callsOut,
                    backgroundColor: '#27c676'
                }, {
                    label: 'Missed',
                    data: callsMissed,
                    backgroundColor: '#f76255'
                }]
            };
            chartOptions = {
                scales: {
                    xAxes: [{
                        stacked: true
                    }],
                    yAxes: [{
                        stacked: true,
                        ticks: {
                            beginAtZero: true,
                            min: 0,
                            callback: function(value, index, values) {
                                if (Math.floor(value) === value) {
                                    return value
                                }
                            }
                        }
                    }]
                }
            }
        }
        lineChart = new Chart(chart_2,{
            type: type,
            data: chartData,
            options: chartOptions
        })
    }
    function getDate() {
        var date = new Date;
        date.setDate(date.getDate());
        var dd = date.getDate().toString();
        var mm = (date.getMonth() + 1).toString();
        var hh = date.getHours().toString();
        var yyyy = date.getFullYear().toString();
        var last_dd = dd - 1
          , last_mm = mm - 1;
        if (dd < 10)
            dd = '0' + dd;
        if (mm < 10)
            mm = '0' + mm;
        if (hh < 10)
            hh = '0' + hh;
        if (last_dd < 10)
            last_dd = '0' + last_dd;
        if (last_mm < 10)
            last_mm = '0' + last_mm;
        var formatted = {
            hour: (hh + dd + mm + yyyy).toString(),
            day: (dd + mm + yyyy).toString(),
            today: (dd + mm + yyyy).toString(),
            today_hours: [],
            yesterday: (last_dd + mm + yyyy).toString(),
            yesterday_hours: [],
            this_week_days: [],
            last_week_days: [],
            month: (mm + yyyy).toString(),
            last_month: (last_mm + yyyy).toString(),
            this_month: [],
            last_month: []
        };
        var dayNum = date.getDay();
        for (var i = 0; i < dayNum; i++) {
            var dayBefore = date - 1000 * 60 * 60 * 24 * i;
            formatted.this_week_days.push({
                date: getApiFormattedDate(dayBefore),
                epoch: dayBefore
            });
            if (i == dayNum - 1) {
                for (var j = 0; j < 7; j++) {
                    var lastWeekDay = date - 1000 * 60 * 60 * 24 * (i + j + 1);
                    formatted.last_week_days.push({
                        date: getApiFormattedDate(lastWeekDay),
                        epoch: lastWeekDay
                    })
                }
            }
        }
        for (var j = 0; j < 24; j++) {
            var hour;
            if (j < 10) {
                hour = '0' + j
            } else {
                hour = j
            }
            formatted.today_hours.push({
                date: hour + formatted.today,
                hour: hour
            });
            formatted.yesterday_hours.push({
                date: hour + formatted.yesterday,
                hour: hour
            })
        }
        let thisMonthLenght = new Date(parseInt(yyyy),parseInt(date.getMonth()),0).getDate();
        for (var t = 1; t <= thisMonthLenght; t++) {
            if (t <= date.getDate()) {
                if (t < 10)
                    t = '0' + t;
                let d = t.toString() + mm + yyyy;
                formatted.this_month.push({
                    date: d
                })
            }
        }
        let lastMonthLenght;
        if (date.getMonth() === 0) {
            let lastYear = parseInt(yyyy) - 1;
            lastMonthLenght = new Date(lastYear,12,0).getDate();
            for (let l = 1; l <= lastMonthLenght; l++) {
                if (l < 10)
                    l = '0' + l;
                let d = l.toString() + '12' + lastYear;
                formatted.last_month.push({
                    date: d
                })
            }
        } else {
            lastMonthLenght = new Date(parseInt(yyyy),parseInt(date.getMonth()) - 1,0).getDate();
            for (let l = 1; l <= lastMonthLenght; l++) {
                if (l < 10)
                    l = '0' + l;
                let d = l.toString() + mm + yyyy;
                formatted.last_month.push({
                    date: d
                })
            }
        }
        return formatted
    }
    function getWeek() {
        var date = new Date().getTime();
        var week = Math.ceil(date / 1000 / 60 / 60 / 24 / 7);
        return week
    }
    ;function getApiFormattedDate(epoch) {
        var date = new Date(epoch);
        date.setDate(date.getDate());
        var dd = date.getDate().toString();
        var mm = (date.getMonth() + 1).toString();
        var hh = date.getHours().toString();
        var yyyy = date.getFullYear().toString();
        var last_dd = dd - 1
          , last_mm = mm - 1;
        if (dd < 10)
            dd = '0' + dd;
        if (mm < 10)
            mm = '0' + mm;
        if (hh < 10)
            hh = '0' + hh;
        var formattedDate = (dd + mm + yyyy).toString();
        return formattedDate
    }
    async function fetchMultipleUrl(url, params) {
        var urls = [];
        var results = [];
        params.forEach(function(e, id) {
            urls.push({
                url: url + e.date,
                date: e.date
            })
        });
        await Promise.all(urls.map(url=>fetch(url.url).then(checkStatus).then(parseJSON))).then(data=>{
            data.forEach(function(e, id) {
                if (isEmpty(e)) {
                    e = initialData
                }
                results.push({
                    data: e,
                    date: urls[id].date
                })
            });
            return results
        }
        );
        return results
    }
    function checkStatus(response) {
        if (response.ok) {
            return Promise.resolve(response)
        } else {
            return Promise.reject(new Error(response.statusText))
        }
    }
    function parseJSON(response) {
        return response.json()
    }
    function isEmpty(obj) {
        for (var key in obj) {
            if (obj.hasOwnProperty(key))
                return false
        }
        return true
    }
    function statsSum(e) {
        var stats = {
            calls: 0,
            incalls: 0,
            outcalls: 0,
            missed: 0,
            durationivr: 0,
            durationring: 0,
            durationtalk: 0,
            durationhold: 0,
            durationidle: 0,
            avgdurationring: 0,
            avgdurationivr: 0,
            rw: 0,
            rr: 0,
            ra: 0,
            hc: 0,
            hw: 0,
            hr: 0,
            ui: 0,
            ai: 0,
            mc: 0,
            soap: 0,
            other: 0,
            calllist: []
        };
        e.forEach(function(data, key, array) {
            stats.calls = stats.calls + data.data.calls;
            stats.incalls = stats.incalls + data.data.incalls;
            stats.outcalls = stats.outcalls + data.data.outcalls;
            stats.missed = stats.missed + data.data.missed;
            stats.hr = stats.hr + data.data.hr;
            stats.hw = stats.hw + data.data.hw;
            stats.hc = stats.hc + data.data.hc;
            stats.ra = stats.ra + data.data.ra;
            stats.rr = stats.rr + data.data.rr;
            stats.rw = stats.rw + data.data.rw;
            stats.durationring = stats.durationring + data.data.durationring;
            stats.durationivr = stats.durationivr + data.data.durationivr;
            stats.durationtalk = stats.durationtalk + data.data.durationtalk;
            stats.durationhold = stats.durationhold + data.data.durationhold;
            stats.durationidle = stats.durationidle + data.data.durationidle;
            stats.calllist = stats.calllist.concat(data.data.calllist);
            if (Object.is(array.length - 1, key)) {
                stats.avgdurationivr = stats.durationring / stats.calls;
                stats.avgdurationring = stats.durationivr / stats.calls
            }
        });
        return stats
    }
    function loginUser(ext) {
        acdLogin(ext, 'true')
    }
    function logoutUser(ext) {
        acdLogin(ext, 'false')
    }
    function acdLogin(ext) {
        var action;
        var c = agents[ext];
        if (c.classList.contains('logged-in')) {
            action = false
        } else {
            action = true
        }
        fetch(pbx.getServer() + '/rest/domain/' + pbx.getDn() + '/account/' + encodeURIComponent(ext) + '/acd_login?group=' + encodeURIComponent(selectedAcd), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify({
                login: action
            })
        }).then(res=>res.json()).then(data=>{
            let acdButtonStatus = document.getElementById('pbx-acd-button-' + selectedAcd).getElementsByTagName('div')[0];
            if (c.classList.contains('logged-in')) {
                c.classList.remove('logged-in');
                c.classList.add('logged-out');
                acdButtonStatus.classList.remove('in');
                acdButtonStatus.classList.add('out')
            } else {
                c.classList.remove('logged-out');
                c.classList.add('logged-in');
                acdButtonStatus.classList.remove('out');
                acdButtonStatus.classList.add('in')
            }
        }
        ).catch(err=>console.log(err))
    }
    async function acdSettings(id) {
        let url = pbx.getServer() + '/rest/domain/' + pbx.getDn() + '/user_settings/' + encodeURIComponent(id);
        try {
            let res = await fetch(url);
            acdSettingsData[id] = await res.json()
        } catch (e) {
            console.log(e)
        }
    }
    function acdJoin(id) {
        let agents;
        let url = pbx.getServer() + '/rest/domain/' + pbx.getDn() + '/user_settings/' + encodeURIComponent(id);
        if (acdSettingsData[id].all_agents.length !== 0) {
            agents = acdSettingsData[id].all_agents + ' ' + pbx.getUn()
        } else {
            agents = pbx.getUn()
        }
        let data = {
            all_agents: agents
        };
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(data)
        }).then(res=>{
            acdSettingsData[id].all_agents = agents;
            updateAcdButton(id)
        }
        )
    }
    function acdLeave(id) {
        let settings = acdSettingsData[id];
        let agentList = settings.all_agents;
        let agentId = pbx.getUn();
        let newList;
        let strIndex = agentList.indexOf(agentId);
        let strLenght = agentId.length;
        if (strIndex !== -1) {
            if (strIndex === 0 && agentList.length == strLenght) {
                newList = agentList.replace(pbx.getUn(), '')
            } else if (strIndex === 0) {
                newList = agentList.replace(pbx.getUn() + ' ', '')
            } else {
                newList = agentList.replace(' ' + pbx.getUn(), '')
            }
        }
        let url = pbx.getServer() + '/rest/domain/' + pbx.getDn() + '/user_settings/' + encodeURIComponent(id);
        let data = {
            all_agents: newList
        };
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(data)
        }).then(res=>{
            acdSettingsData[id].all_agents = newList;
            updateAcdButton(id)
        }
        )
    }
    async function updateAcdButton(id) {
        let isMember;
        let button = document.getElementById('pbx-acd-button-' + id);
        let loginStatus = button.getElementsByTagName('div')[0];
        let wrap = button.parentNode;
        loginStatus.classList.contains('in') || loginStatus.classList.contains('out') ? isMember = true : isMember = false;
        document.body.classList.add('loading');
        let statusCheck = setInterval(async function() {
            let wallData = await loadWallboardAcd(id);
            let agents = wallData.agents;
            let results = agents.filter(function(entry) {
                return entry.account === pbx.getUn()
            });
            loginStatus.className = '';
            if (isMember) {
                if (results.length === 0) {
                    clearInterval(statusCheck);
                    wrap.className = 'pbx-acd-add-current-agent';
                    reloadAcdPage(id);
                    document.body.classList.remove('loading')
                }
            }
            if (!isMember) {
                if (results.length > 0) {
                    clearInterval(statusCheck);
                    wrap.className = 'pbx-acd-remove-current-agent';
                    if (results[0].login) {
                        loginStatus.classList = 'in'
                    } else {
                        loginStatus.classList = 'out'
                    }
                    reloadAcdPage(id);
                    document.body.classList.remove('loading')
                }
            }
        }, 1000)
    }
    function reloadAcdPage(id) {
        if (id === acdId) {
            loadWallboard(currentWallboardData, currentWallboardEvt)
        }
    }
    document.addEventListener('pbx.notify.state', function(e) {});
    document.getElementById('acd-cdr-close').addEventListener('click', hideCdr);
    document.getElementById('menu-acd').addEventListener('click', loadAcd)
}, false);
