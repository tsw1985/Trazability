
if(typeof(RGraph)=='undefined')RGraph={isRGraph:true,type:'common'};RGraph.Highlight={};RGraph.Registry={};RGraph.Registry.store=[];RGraph.Registry.store['chart.event.handlers']=[];RGraph.Registry.store['__rgraph_event_listeners__']=[];RGraph.background={};RGraph.objects=[];RGraph.Resizing={};RGraph.events=[];RGraph.cursor=[];RGraph.ObjectRegistry={};RGraph.ObjectRegistry.objects={};RGraph.ObjectRegistry.objects.byUID=[];RGraph.ObjectRegistry.objects.byCanvasID=[];PI=Math.PI;HALFPI=PI/2;TWOPI=PI*2;ISFF=navigator.userAgent.indexOf('Firefox')!=-1;ISOPERA=navigator.userAgent.indexOf('Opera')!=-1;ISCHROME=navigator.userAgent.indexOf('Chrome')!=-1;ISSAFARI=navigator.userAgent.indexOf('Safari')!=-1&&!ISCHROME;ISWEBKIT=navigator.userAgent.indexOf('WebKit')!=-1;RGraph.getScale=function(max,obj)
{if(max==0){return['0.2','0.4','0.6','0.8','1.0'];}
var original_max=max;if(max<=1){if(max>0.5){return[0.2,0.4,0.6,0.8,Number(1).toFixed(1)];}else if(max>=0.1){return obj.Get('chart.scale.round')?[0.2,0.4,0.6,0.8,1]:[0.1,0.2,0.3,0.4,0.5];}else{var tmp=max;var exp=0;while(tmp<1.01){exp+=1;tmp*=10;}
var ret=['2e-'+exp,'4e-'+exp,'6e-'+exp,'8e-'+exp,'10e-'+exp];if(max<=('5e-'+exp)){ret=['1e-'+exp,'2e-'+exp,'3e-'+exp,'4e-'+exp,'5e-'+exp];}
return ret;}}
if(String(max).indexOf('.')>0){max=String(max).replace(/\.\d+$/,'');}
var interval=Math.pow(10,Number(String(Number(max)).length-1));var topValue=interval;while(topValue<max){topValue+=(interval/2);}
if(Number(original_max)>Number(topValue)){topValue+=(interval/2);}
if(max<10){topValue=(Number(original_max)<=5?5:10);}
if(obj&&typeof(obj.Get('chart.scale.round'))=='boolean'&&obj.Get('chart.scale.round')){topValue=10*interval;}
return[topValue*0.2,topValue*0.4,topValue*0.6,topValue*0.8,topValue];}
RGraph.getScale2=function(obj,opt)
{var RG=RGraph;var ca=obj.canvas;var co=obj.context;var prop=obj.properties;var numlabels=typeof(opt['ylabels.count'])=='number'?opt['ylabels.count']:5;var units_pre=typeof(opt['units.pre'])=='string'?opt['units.pre']:'';var units_post=typeof(opt['units.post'])=='string'?opt['units.post']:'';var max=Number(opt['max']);var min=typeof(opt['min'])=='number'?opt['min']:0;var strict=opt['strict'];var decimals=Number(opt['scale.decimals']);var point=opt['scale.point'];var thousand=opt['scale.thousand'];var original_max=max;var round=opt['scale.round'];var scale={'max':1,'labels':[]};if(!max){var max=1;var scale={max:1,min:0,labels:[]};for(var i=0;i<numlabels;++i){var label=((((max-min)/numlabels)+min)*(i+1)).toFixed(decimals);scale.labels.push(units_pre+label+units_post);}}else if(max<=1&&!strict){if(max>0.5){max=1;min=min;scale.min=min;for(var i=0;i<numlabels;++i){var label=((((max-min)/numlabels)*(i+1))+min).toFixed(decimals);scale.labels.push(units_pre+label+units_post);}}else if(max>=0.1){max=0.5;min=min;scale={'max':0.5,'min':min,'labels':[]}
for(var i=0;i<numlabels;++i){var label=((((max-min)/numlabels)+min)*(i+1)).toFixed(decimals);scale.labels.push(units_pre+label+units_post);}}else{scale={'min':min,'labels':[]}
var max_str=String(max);if(max_str.indexOf('e')>0){var numdecimals=Math.abs(max_str.substring(max_str.indexOf('e')+1));}else{var numdecimals=String(max).length-2;}
var max=1/Math.pow(10,numdecimals-1);for(var i=0;i<numlabels;++i){var label=((((max-min)/numlabels)+min)*(i+1));label=label.toExponential();label=label.split(/e/);label[0]=Math.round(label[0]);label=label.join('e');scale.labels.push(label);}
tmp=scale.labels[scale.labels.length-1].split(/e/);tmp[0]+=0;tmp[1]=Number(tmp[1])-1;tmp=tmp[0]+'e'+tmp[1];scale.labels[scale.labels.length-1]=tmp;for(var i=0;i<scale.labels.length;++i){scale.labels[i]=units_pre+scale.labels[i]+units_post;}
scale.max=Number(max);}}else if(!strict){max=Math.ceil(max);var interval=Math.pow(10,Math.max(1,Number(String(Number(max)-Number(min)).length-1)));var topValue=interval;while(topValue<max){topValue+=(interval/2);}
if(Number(original_max)>Number(topValue)){topValue+=(interval/2);}
if(max<=10){topValue=(Number(original_max)<=5?5:10);}
if(obj&&typeof(round)=='boolean'&&round){topValue=10*interval;}
scale.max=topValue;var tmp_point=prop['chart.scale.point'];var tmp_thousand=prop['chart.scale.thousand'];obj.Set('chart.scale.thousand',thousand);obj.Set('chart.scale.point',point);for(var i=0;i<numlabels;++i){scale.labels.push(RG.number_format(obj,((((i+1)/numlabels)*(topValue-min))+min).toFixed(decimals),units_pre,units_post));}
obj.Set('chart.scale.thousand',tmp_thousand);obj.Set('chart.scale.point',tmp_point);}else if(typeof(max)=='number'&&strict){for(var i=0;i<numlabels;++i){scale.labels.push(RG.number_format(obj,((((i+1)/numlabels)*(max-min))+min).toFixed(decimals),units_pre,units_post));}
scale.max=max;}
scale.units_pre=units_pre;scale.units_post=units_post;scale.point=point;scale.decimals=decimals;scale.thousand=thousand;scale.numlabels=numlabels;scale.round=Boolean(round);scale.min=min;return scale;}
RGraph.array_max=function(arr)
{var max=null;var MathLocal=Math;if(typeof(arr)=='number'){return arr;}
if(RGraph.is_null(arr)){return 0;}
for(var i=0,len=arr.length;i<len;++i){if(typeof(arr[i])=='number'){var val=arguments[1]?MathLocal.abs(arr[i]):arr[i];if(typeof max=='number'){max=MathLocal.max(max,val);}else{max=val;}}}
return max;}
RGraph.array_pad=function(arr,len)
{if(arr.length<len){var val=arguments[2]?arguments[2]:null;for(var i=arr.length;i<len;i+=1){arr[i]=val;}}
return arr;}
RGraph.array_sum=function(arr)
{if(typeof(arr)=='number'){return arr;}
if(RGraph.is_null(arr)){return 0;}
var i,sum;var len=arr.length;for(i=0,sum=0;i<len;sum+=arr[i++]);return sum;}
RGraph.array_linearize=function()
{var arr=[];var args=arguments;var RG=RGraph;for(var i=0,len=args.length;i<len;++i){if(typeof(args[i])=='object'&&args[i]){for(var j=0;j<args[i].length;++j){var sub=RG.array_linearize(args[i][j]);for(var k=0;k<sub.length;++k){arr.push(sub[k]);}}}else{arr.push(args[i]);}}
return arr;}
RGraph.Text=function(context,font,size,x,y,text)
{var args=arguments;if((typeof(text)!='string'&&typeof(text)!='number')||text=='undefined'){return;}
if(typeof(text)=='string'&&text.match(/\r\n/)){var dimensions=RGraph.MeasureText('M',args[11],font,size);var arr=text.split('\r\n');if(args[6]&&args[6]=='center')y=(y-(dimensions[1]*((arr.length-1)/2)));for(var i=1;i<arr.length;++i){RGraph.Text(context,font,size,args[9]==-90?(x+(size*1.5)):x,y+(dimensions[1]*i),arr[i],args[6]?args[6]:null,args[7],args[8],args[9],args[10],args[11],args[12]);}
text=arr[0];}
if(document.all&&ISOLD){y+=2;}
context.font=(args[11]?'Bold ':'')+size+'pt '+font;var i;var origX=x;var origY=y;var originalFillStyle=context.fillStyle;var originalLineWidth=context.lineWidth;if(typeof(args[6])=='undefined')args[6]='bottom';if(typeof(args[7])=='undefined')args[7]='left';if(typeof(args[8])=='undefined')args[8]=null;if(typeof(args[9])=='undefined')args[9]=0;if(navigator.userAgent.indexOf('Opera')!=-1){context.canvas.__rgraph_valign__=args[6];context.canvas.__rgraph_halign__=args[7];}
context.save();context.canvas.__rgraph_originalx__=x;context.canvas.__rgraph_originaly__=y;context.translate(x,y);x=0;y=0;if(args[9]){context.rotate(args[9]/(180/PI));}
if(args[6]){var vAlign=args[6];if(vAlign=='center'){context.textBaseline='middle';}else if(vAlign=='top'){context.textBaseline='top';}}
if(args[7]){var hAlign=args[7];var width=context.measureText(text).width;if(hAlign){if(hAlign=='center'){context.textAlign='center';}else if(hAlign=='right'){context.textAlign='right';}}}
context.fillStyle=originalFillStyle;context.save();context.fillText(text,0,0);context.lineWidth=1;var width=context.measureText(text).width;var width_offset=(hAlign=='center'?(width/2):(hAlign=='right'?width:0));var height=size*1.5;var height_offset=(vAlign=='center'?(height/2):(vAlign=='top'?height:0));var ieOffset=ISOLD?2:0;if(args[8]){context.strokeRect(-3-width_offset,0-3-height-ieOffset+height_offset,width+6,height+6);if(args[10]){context.fillStyle=args[10];context.fillRect(-3-width_offset,0-3-height-ieOffset+height_offset,width+6,height+6);}
context.fillStyle=originalFillStyle;context.fillText(text,0,0);}
context.restore();context.lineWidth=originalLineWidth;context.restore();}
RGraph.Clear=function(ca)
{var RG=RGraph;var co=ca.getContext('2d');var color=arguments[1];if(!ca){return;}
RG.FireCustomEvent(ca.__object__,'onbeforeclear');if(ISIE8&&!color){color='white';}
if(!color||(color&&color=='rgba(0,0,0,0)'||color=='transparent')){co.clearRect(0,0,ca.width,ca.height);co.globalCompositeOperation='source-over';}else{co.fillStyle=color;co.beginPath();if(ISIE8){co.fillRect(0,0,ca.width,ca.height);}else{co.fillRect(-10,-10,ca.width+20,ca.height+20);}
co.fill();}
if(RG.Registry.Get('chart.background.image.'+ca.id)){var img=RG.Registry.Get('chart.background.image.'+ca.id);img.style.position='absolute';img.style.left='-10000px';img.style.top='-10000px';}
if(RG.Registry.Get('chart.tooltip')){RG.HideTooltip(ca);}
ca.style.cursor='default';RG.FireCustomEvent(ca.__object__,'onclear');}
RGraph.DrawTitle=function(obj,text,gutterTop)
{var RG=RGraph;var ca=canvas=obj.canvas;var co=context=obj.context;var prop=obj.properties;var gutterLeft=prop['chart.gutter.left'];var gutterRight=prop['chart.gutter.right'];var gutterTop=gutterTop;var gutterBottom=prop['chart.gutter.bottom'];var size=arguments[4]?arguments[4]:12;var bold=prop['chart.title.bold'];var centerx=(arguments[3]?arguments[3]:((ca.width-gutterLeft-gutterRight)/2)+gutterLeft);var keypos=prop['chart.key.position'];var vpos=prop['chart.title.vpos'];var hpos=prop['chart.title.hpos'];var bgcolor=prop['chart.title.background'];var x=prop['chart.title.x'];var y=prop['chart.title.y'];var halign='center';var valign='center';if(obj.type=='bar'&&prop['chart.variant']=='3d'){keypos='gutter';}
co.beginPath();co.fillStyle=prop['chart.text.color']?prop['chart.text.color']:'black';if(keypos&&keypos!='gutter'){var valign='center';}else if(!keypos){var valign='center';}else{var valign='bottom';}
if(typeof(prop['chart.title.vpos'])=='number'){vpos=prop['chart.title.vpos']*gutterTop;if(prop['chart.xaxispos']=='top'){vpos=prop['chart.title.vpos']*gutterBottom+gutterTop+(ca.height-gutterTop-gutterBottom);}}else{vpos=gutterTop-size-5;if(prop['chart.xaxispos']=='top'){vpos=ca.height-gutterBottom+size+5;}}
if(typeof(hpos)=='number'){centerx=hpos*ca.width;}
if(typeof(x)=='number')centerx=x;if(typeof(y)=='number')vpos=y;if(typeof(prop['chart.title.halign'])=='string'){halign=prop['chart.title.halign'];}
if(typeof(prop['chart.title.valign'])=='string'){valign=prop['chart.title.valign'];}
if(typeof(prop['chart.title.color']!=null)){var oldColor=co.fillStyle
var newColor=prop['chart.title.color']
co.fillStyle=newColor?newColor:'black';}
var font=prop['chart.text.font'];if(typeof(prop['chart.title.font'])=='string'){font=prop['chart.title.font'];}
RG.Text2(obj,{'font':font,'size':size,'x':centerx,'y':vpos,'text':text,'valign':valign,'halign':halign,'bounding':bgcolor!=null,'bounding.fill':bgcolor,'bold':bold,'tag':'title'});co.fillStyle=oldColor;}
RGraph.getMouseXY=function(e)
{var el=e.target;var ca=el;var caStyle=ca.style;var offsetX=0;var offsetY=0;var x;var y;var ISFIXED=(ca.style.position=='fixed');var borderLeft=parseInt(caStyle.borderLeftWidth)||0;var borderTop=parseInt(caStyle.borderTopWidth)||0;var paddingLeft=parseInt(caStyle.paddingLeft)||0
var paddingTop=parseInt(caStyle.paddingTop)||0
var additionalX=borderLeft+paddingLeft;var additionalY=borderTop+paddingTop;if(typeof(e.offsetX)=='number'&&typeof(e.offsetY)=='number'){if(ISFIXED){if(ISOPERA){x=e.offsetX;y=e.offsetY;}else if(ISWEBKIT){x=e.offsetX-paddingLeft-borderLeft;y=e.offsetY-paddingTop-borderTop;}else if(ISIE){x=e.offsetX-paddingLeft;y=e.offsetY-paddingTop;}else{x=e.offsetX;y=e.offsetY;}}else{if(!ISIE&&!ISOPERA){x=e.offsetX-borderLeft-paddingLeft;y=e.offsetY-borderTop-paddingTop;}else if(ISIE){x=e.offsetX-paddingLeft;y=e.offsetY-paddingTop;}else{x=e.offsetX;y=e.offsetY;}}}else{if(typeof(el.offsetParent)!='undefined'){do{offsetX+=el.offsetLeft;offsetY+=el.offsetTop;}while((el=el.offsetParent));}
x=e.pageX-offsetX-additionalX;y=e.pageY-offsetY-additionalY;x-=(2*(parseInt(document.body.style.borderLeftWidth)||0));y-=(2*(parseInt(document.body.style.borderTopWidth)||0));}
return[x,y];}
RGraph.getCanvasXY=function(canvas)
{var x=0;var y=0;var el=canvas;do{x+=el.offsetLeft;y+=el.offsetTop;if(el.tagName.toLowerCase()=='table'&&(ISCHROME||ISSAFARI)){x+=parseInt(el.border)||0;y+=parseInt(el.border)||0;}
el=el.offsetParent;}while(el&&el.tagName.toLowerCase()!='body');var paddingLeft=canvas.style.paddingLeft?parseInt(canvas.style.paddingLeft):0;var paddingTop=canvas.style.paddingTop?parseInt(canvas.style.paddingTop):0;var borderLeft=canvas.style.borderLeftWidth?parseInt(canvas.style.borderLeftWidth):0;var borderTop=canvas.style.borderTopWidth?parseInt(canvas.style.borderTopWidth):0;if(navigator.userAgent.indexOf('Firefox')>0){x+=parseInt(document.body.style.borderLeftWidth)||0;y+=parseInt(document.body.style.borderTopWidth)||0;}
return[x+paddingLeft+borderLeft,y+paddingTop+borderTop];}
RGraph.isFixed=function(canvas)
{var obj=canvas;var i=0;while(obj.tagName.toLowerCase()!='body'&&i<99){if(obj.style.position=='fixed'){return obj;}
obj=obj.offsetParent;}
return false;}
RGraph.Register=function(obj)
{if(!obj.Get('chart.noregister')){RGraph.ObjectRegistry.Add(obj);obj.Set('chart.noregister',true);}}
RGraph.Redraw=function()
{var objectRegistry=RGraph.ObjectRegistry.objects.byCanvasID;var tags=document.getElementsByTagName('canvas');for(var i=0,len=tags.length;i<len;++i){if(tags[i].__object__&&tags[i].__object__.isRGraph){if(!tags[i].noclear){RGraph.Clear(tags[i],arguments[0]?arguments[0]:null);}}}
for(var i=0,len=objectRegistry.length;i<len;++i){if(objectRegistry[i]){var id=objectRegistry[i][0];objectRegistry[i][1].Draw();}}}
RGraph.RedrawCanvas=function(canvas)
{var objects=RGraph.ObjectRegistry.getObjectsByCanvasID(canvas.id);if(!arguments[1]||(typeof(arguments[1])=='boolean'&&!arguments[1]==false)){RGraph.Clear(canvas);}
for(var i=0,len=objects.length;i<len;++i){if(objects[i]){if(objects[i]&&objects[i].isRGraph){objects[i].Draw();}}}}
RGraph.background.Draw=function(obj)
{var RG=RGraph;var ca=canvas=obj.canvas;var co=context=obj.context;var prop=obj.properties;var height=0;var gutterLeft=obj.gutterLeft;var gutterRight=obj.gutterRight;var gutterTop=obj.gutterTop;var gutterBottom=obj.gutterBottom;var variant=prop['chart.variant'];co.fillStyle=prop['chart.text.color'];if(variant=='3d'){co.save();co.translate(10,-5);}
if(typeof(prop['chart.title.xaxis'])=='string'&&prop['chart.title.xaxis'].length){var size=prop['chart.text.size']+2;var font=prop['chart.text.font'];var bold=prop['chart.title.xaxis.bold'];if(typeof(prop['chart.title.xaxis.size'])=='number'){size=prop['chart.title.xaxis.size'];}
if(typeof(prop['chart.title.xaxis.font'])=='string'){font=prop['chart.title.xaxis.font'];}
var hpos=((ca.width-gutterLeft-gutterRight)/2)+gutterLeft;var vpos=ca.height-gutterBottom+25;if(typeof(prop['chart.title.xaxis.pos'])=='number'){vpos=ca.height-(gutterBottom*prop['chart.title.xaxis.pos']);}
RG.Text2(obj,{'font':font,'size':size,'x':hpos,'y':vpos,'text':prop['chart.title.xaxis'],'halign':'center','valign':'center','bold':bold,'tag':'title xaxis'});}
if(typeof(prop['chart.title.yaxis'])=='string'&&prop['chart.title.yaxis'].length){var size=prop['chart.text.size']+2;var font=prop['chart.text.font'];var angle=270;var bold=prop['chart.title.yaxis.bold'];var color=prop['chart.title.yaxis.color'];if(typeof(prop['chart.title.yaxis.pos'])=='number'){var yaxis_title_pos=prop['chart.title.yaxis.pos']*gutterLeft;}else{var yaxis_title_pos=((gutterLeft-25)/gutterLeft)*gutterLeft;}
if(typeof(prop['chart.title.yaxis.size'])=='number'){size=prop['chart.title.yaxis.size'];}
if(typeof(prop['chart.title.yaxis.font'])=='string'){font=prop['chart.title.yaxis.font'];}
if(prop['chart.title.yaxis.align']=='right'||prop['chart.title.yaxis.position']=='right'){angle=90;yaxis_title_pos=prop['chart.title.yaxis.pos']?(ca.width-gutterRight)+(prop['chart.title.yaxis.pos']*gutterRight):ca.width-gutterRight+prop['chart.text.size']+5;}else{yaxis_title_pos=yaxis_title_pos;}
co.fillStyle=color;RG.Text2(obj,{'font':font,'size':size,'x':yaxis_title_pos,'y':((ca.height-gutterTop-gutterBottom)/2)+gutterTop,'valign':'center','halign':'center','angle':angle,'bold':bold,'text':prop['chart.title.yaxis'],'tag':'title yaxis'});}
var bgcolor=prop['chart.background.color'];if(bgcolor){co.fillStyle=bgcolor;co.fillRect(gutterLeft,gutterTop,ca.width-gutterLeft-gutterRight,ca.height-gutterTop-gutterBottom);}
co.beginPath();co.fillStyle=prop['chart.background.barcolor1'];co.strokeStyle=co.fillStyle;height=(ca.height-gutterBottom);for(var i=gutterTop;i<height;i+=80){co.fillRect(gutterLeft,i,ca.width-gutterLeft-gutterRight,Math.min(40,ca.height-gutterBottom-i));}
co.fillStyle=prop['chart.background.barcolor2'];co.strokeStyle=co.fillStyle;height=(ca.height-gutterBottom);for(var i=(40+gutterTop);i<height;i+=80){co.fillRect(gutterLeft,i,ca.width-gutterLeft-gutterRight,i+40>(ca.height-gutterBottom)?ca.height-(gutterBottom+i):40);}
co.beginPath();if(prop['chart.background.grid']){if(prop['chart.background.grid.autofit']){if(prop['chart.background.grid.autofit.align']){obj.Set('chart.background.grid.autofit.numhlines',prop['chart.ylabels.count']);if(obj.type=='line'){if(prop['chart.labels']&&prop['chart.labels'].length){obj.Set('chart.background.grid.autofit.numvlines',prop['chart.labels'].length-1);}else{obj.Set('chart.background.grid.autofit.numvlines',obj.data[0].length-1);}}else if(obj.type=='bar'&&prop['chart.labels']&&prop['chart.labels'].length){obj.Set('chart.background.grid.autofit.numvlines',prop['chart.labels'].length);}}
var vsize=((ca.width-gutterLeft-gutterRight))/prop['chart.background.grid.autofit.numvlines'];var hsize=(ca.height-gutterTop-gutterBottom)/prop['chart.background.grid.autofit.numhlines'];obj.Set('chart.background.grid.vsize',vsize);obj.Set('chart.background.grid.hsize',hsize);}
co.beginPath();co.lineWidth=prop['chart.background.grid.width']?prop['chart.background.grid.width']:1;co.strokeStyle=prop['chart.background.grid.color'];if(prop['chart.background.grid.dashed']&&typeof co.setLineDash=='function'){co.setLineDash([3,2]);}
if(prop['chart.background.grid.dotted']&&typeof co.setLineDash=='function'){co.setLineDash([1,2]);}
if(prop['chart.background.grid.hlines']){height=(ca.height-gutterBottom)
for(y=gutterTop;y<height;y+=prop['chart.background.grid.hsize']){context.moveTo(gutterLeft,Math.round(y));context.lineTo(ca.width-gutterRight,Math.round(y));}}
if(prop['chart.background.grid.vlines']){var width=(ca.width-gutterRight)
for(x=gutterLeft;x<=width;x+=prop['chart.background.grid.vsize']){co.moveTo(Math.round(x),gutterTop);co.lineTo(Math.round(x),ca.height-gutterBottom);}}
if(prop['chart.background.grid.border']){co.strokeStyle=prop['chart.background.grid.color'];co.strokeRect(Math.round(gutterLeft),Math.round(gutterTop),ca.width-gutterLeft-gutterRight,ca.height-gutterTop-gutterBottom);}}
context.stroke();if(typeof co.setLineDash=='function'){co.setLineDash(null);}
if(variant=='3d'){co.restore();}
if(typeof(prop['chart.title'])=='string'){if(obj.type=='gantt'){gutterTop-=10;}
RG.DrawTitle(obj,prop['chart.title'],gutterTop,null,prop['chart.title.size']?prop['chart.title.size']:prop['chart.text.size']+2);}
co.stroke();}
RGraph.array_clone=function(obj)
{var RG=RGraph;if(obj==null||typeof(obj)!='object'){return obj;}
var temp=[];for(var i=0,len=obj.length;i<len;++i){if(typeof(obj[i])=='number'){temp[i]=(function(arg){return Number(arg);})(obj[i]);}else if(typeof(obj[i])=='string'){temp[i]=(function(arg){return String(arg);})(obj[i]);}else if(typeof(obj[i])=='function'){temp[i]=obj[i];}else{temp[i]=RG.array_clone(obj[i]);}}
return temp;}
RGraph.number_format=function(obj,num)
{var RG=RGraph;var ca=obj.canvas;var co=obj.context;var prop=obj.properties;var i;var prepend=arguments[2]?String(arguments[2]):'';var append=arguments[3]?String(arguments[3]):'';var output='';var decimal='';var decimal_seperator=typeof(prop['chart.scale.point'])=='string'?prop['chart.scale.point']:'.';var thousand_seperator=typeof(prop['chart.scale.thousand'])=='string'?prop['chart.scale.thousand']:',';RegExp.$1='';var i,j;if(typeof(prop['chart.scale.formatter'])=='function'){return prop['chart.scale.formatter'](obj,num);}
if(String(num).indexOf('e')>0){return String(prepend+String(num)+append);}
num=String(num);if(num.indexOf('.')>0){var tmp=num;num=num.replace(/\.(.*)/,'');decimal=tmp.replace(/(.*)\.(.*)/,'$2');}
var seperator=thousand_seperator;var foundPoint;for(i=(num.length-1),j=0;i>=0;j++,i--){var character=num.charAt(i);if(j%3==0&&j!=0){output+=seperator;}
output+=character;}
var rev=output;output='';for(i=(rev.length-1);i>=0;i--){output+=rev.charAt(i);}
if(output.indexOf('-'+prop['chart.scale.thousand'])==0){output='-'+output.substr(('-'+prop['chart.scale.thousand']).length);}
if(decimal.length){output=output+decimal_seperator+decimal;decimal='';RegExp.$1='';}
if(output.charAt(0)=='-'){output=output.replace(/-/,'');prepend='-'+prepend;}
return prepend+output+append;}
RGraph.DrawBars=function(obj)
{var prop=obj.properties;var co=obj.context;var ca=obj.canvas;var RG=RGraph;var hbars=prop['chart.background.hbars'];if(hbars===null){return;}
co.beginPath();for(i=0,len=hbars.length;i<len;++i){var start=hbars[i][0];var length=hbars[i][1];var color=hbars[i][2];if(RG.is_null(start))start=obj.scale2.max
if(start>obj.scale2.max)start=obj.scale2.max;if(RG.is_null(length))length=obj.scale2.max-start;if(start+length>obj.scale2.max)length=obj.scale2.max-start;if(start+length<(-1*obj.scale2.max))length=(-1*obj.scale2.max)-start;if(prop['chart.xaxispos']=='center'&&start==obj.scale2.max&&length<(obj.scale2.max*-2)){length=obj.scale2.max*-2;}
var x=prop['chart.gutter.left'];var y=obj.getYCoord(start);var w=ca.width-prop['chart.gutter.left']-prop['chart.gutter.right'];var h=obj.getYCoord(start+length)-y;if(ISOPERA!=-1&&prop['chart.xaxispos']=='center'&&h<0){h*=-1;y=y-h;}
if(prop['chart.xaxispos']=='top'){y=ca.height-y;h*=-1;}
co.fillStyle=color;co.fillRect(x,y,w,h);}}
RGraph.DrawInGraphLabels=function(obj)
{var RG=RGraph;var ca=obj.canvas;var co=obj.context;var prop=obj.properties;var labels=prop['chart.labels.ingraph'];var labels_processed=[];var fgcolor='black';var bgcolor='white';var direction=1;if(!labels){return;}
for(var i=0,len=labels.length;i<len;i+=1){if(typeof(labels[i])=='number'){for(var j=0;j<labels[i];++j){labels_processed.push(null);}}else if(typeof(labels[i])=='string'||typeof(labels[i])=='object'){labels_processed.push(labels[i]);}else{labels_processed.push('');}}
RG.NoShadow(obj);if(labels_processed&&labels_processed.length>0){for(var i=0,len=labels_processed.length;i<len;++i){if(labels_processed[i]){var coords=obj.coords[i];if(coords&&coords.length>0){var x=(obj.type=='bar'?coords[0]+(coords[2]/2):coords[0]);var y=(obj.type=='bar'?coords[1]+(coords[3]/2):coords[1]);var length=typeof(labels_processed[i][4])=='number'?labels_processed[i][4]:25;co.beginPath();co.fillStyle='black';co.strokeStyle='black';if(obj.type=='bar'){if(obj.Get('chart.xaxispos')=='top'){length*=-1;}
if(prop['chart.variant']=='dot'){co.moveTo(Math.round(x),obj.coords[i][1]-5);co.lineTo(Math.round(x),obj.coords[i][1]-5-length);var text_x=Math.round(x);var text_y=obj.coords[i][1]-5-length;}else if(prop['chart.variant']=='arrow'){co.moveTo(Math.round(x),obj.coords[i][1]-5);co.lineTo(Math.round(x),obj.coords[i][1]-5-length);var text_x=Math.round(x);var text_y=obj.coords[i][1]-5-length;}else{co.arc(Math.round(x),y,2.5,0,6.28,0);co.moveTo(Math.round(x),y);co.lineTo(Math.round(x),y-length);var text_x=Math.round(x);var text_y=y-length;}
co.stroke();co.fill();}else if(obj.type=='line'){if(typeof(labels_processed[i])=='object'&&typeof(labels_processed[i][3])=='number'&&labels_processed[i][3]==-1){co.moveTo(Math.round(x),y+5);co.lineTo(Math.round(x),y+5+length);co.stroke();co.beginPath();co.moveTo(Math.round(x),y+5);co.lineTo(Math.round(x)-3,y+10);co.lineTo(Math.round(x)+3,y+10);co.closePath();var text_x=x;var text_y=y+5+length;}else{var text_x=x;var text_y=y-5-length;co.moveTo(Math.round(x),y-5);co.lineTo(Math.round(x),y-5-length);co.stroke();co.beginPath();co.moveTo(Math.round(x),y-5);co.lineTo(Math.round(x)-3,y-10);co.lineTo(Math.round(x)+3,y-10);co.closePath();}
co.fill();}
co.beginPath();co.fillStyle=(typeof(labels_processed[i])=='object'&&typeof(labels_processed[i][1])=='string')?labels_processed[i][1]:'black';RG.Text2(obj,{'font':prop['chart.text.font'],'size':prop['chart.text.size'],'x':text_x,'y':text_y,'text':(typeof(labels_processed[i])=='object'&&typeof(labels_processed[i][0])=='string')?labels_processed[i][0]:labels_processed[i],'valign':'bottom','halign':'center','bounding':true,'bounding.fill':(typeof(labels_processed[i])=='object'&&typeof(labels_processed[i][2])=='string')?labels_processed[i][2]:'white','tag':'labels ingraph'});co.fill();}}}}}
RGraph.FixEventObject=function(e)
{if(ISOLD){var e=event;e.pageX=(event.clientX+document.body.scrollLeft);e.pageY=(event.clientY+document.body.scrollTop);e.target=event.srcElement;if(!document.body.scrollTop&&document.documentElement.scrollTop){e.pageX+=parseInt(document.documentElement.scrollLeft);e.pageY+=parseInt(document.documentElement.scrollTop);}}
if(!e.stopPropagation){e.stopPropagation=function(){window.event.cancelBubble=true;}}
return e;}
RGraph.HideCrosshairCoords=function()
{var RG=RGraph;var div=RG.Registry.Get('chart.coordinates.coords.div');if(div&&div.style.opacity==1&&div.__object__.Get('chart.crosshairs.coords.fadeout')){setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0.9;},50);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0.8;},100);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0.7;},150);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0.6;},200);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0.5;},250);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0.4;},300);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0.3;},350);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0.2;},400);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0.1;},450);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.opacity=0;},500);setTimeout(function(){RG.Registry.Get('chart.coordinates.coords.div').style.display='none';},550);}}
RGraph.Draw3DAxes=function(obj)
{var prop=obj.properties;var co=obj.context;var ca=obj.canvas;var gutterLeft=prop['chart.gutter.left'];var gutterRight=prop['chart.gutter.right'];var gutterTop=prop['chart.gutter.top'];var gutterBottom=prop['chart.gutter.bottom'];co.strokeStyle='#aaa';co.fillStyle='#ddd';co.beginPath();co.moveTo(gutterLeft,gutterTop);co.lineTo(gutterLeft+10,gutterTop-5);co.lineTo(gutterLeft+10,ca.height-gutterBottom-5);co.lineTo(gutterLeft,ca.height-gutterBottom);co.closePath();co.stroke();co.fill();co.beginPath();co.moveTo(gutterLeft,ca.height-gutterBottom);co.lineTo(gutterLeft+10,ca.height-gutterBottom-5);co.lineTo(ca.width-gutterRight+10,ca.height-gutterBottom-5);co.lineTo(ca.width-gutterRight,ca.height-gutterBottom);co.closePath();co.stroke();co.fill();}
RGraph.OldBrowserCompat=function(co)
{if(!co){return;}
if(!co.measureText){co.measureText=function(text)
{var textObj=document.createElement('DIV');textObj.innerHTML=text;textObj.style.position='absolute';textObj.style.top='-100px';textObj.style.left=0;document.body.appendChild(textObj);var width={width:textObj.offsetWidth};textObj.style.display='none';return width;}}
if(!co.fillText){co.fillText=function(text,targetX,targetY)
{return false;}}
if(!co.canvas.addEventListener){window.addEventListener=function(ev,func,bubble)
{return this.attachEvent('on'+ev,func);}
co.canvas.addEventListener=function(ev,func,bubble)
{return this.attachEvent('on'+ev,func);}}}
RGraph.strokedCurvyRect=function(co,x,y,w,h)
{var r=arguments[5]?arguments[5]:3;var corner_tl=(arguments[6]||arguments[6]==null)?true:false;var corner_tr=(arguments[7]||arguments[7]==null)?true:false;var corner_br=(arguments[8]||arguments[8]==null)?true:false;var corner_bl=(arguments[9]||arguments[9]==null)?true:false;co.beginPath();co.moveTo(x+(corner_tl?r:0),y);co.lineTo(x+w-(corner_tr?r:0),y);if(corner_tr){co.arc(x+w-r,y+r,r,PI+HALFPI,TWOPI,false);}
co.lineTo(x+w,y+h-(corner_br?r:0));if(corner_br){co.arc(x+w-r,y-r+h,r,TWOPI,HALFPI,false);}
co.lineTo(x+(corner_bl?r:0),y+h);if(corner_bl){co.arc(x+r,y-r+h,r,HALFPI,PI,false);}
co.lineTo(x,y+(corner_tl?r:0));if(corner_tl){co.arc(x+r,y+r,r,PI,PI+HALFPI,false);}
co.stroke();}
RGraph.filledCurvyRect=function(co,x,y,w,h)
{var r=arguments[5]?arguments[5]:3;var corner_tl=(arguments[6]||arguments[6]==null)?true:false;var corner_tr=(arguments[7]||arguments[7]==null)?true:false;var corner_br=(arguments[8]||arguments[8]==null)?true:false;var corner_bl=(arguments[9]||arguments[9]==null)?true:false;co.beginPath();if(corner_tl){co.moveTo(x+r,y+r);co.arc(x+r,y+r,r,PI,PI+HALFPI,false);}else{co.fillRect(x,y,r,r);}
if(corner_tr){co.moveTo(x+w-r,y+r);co.arc(x+w-r,y+r,r,PI+HALFPI,0,false);}else{co.moveTo(x+w-r,y);co.fillRect(x+w-r,y,r,r);}
if(corner_br){co.moveTo(x+w-r,y+h-r);co.arc(x+w-r,y-r+h,r,0,HALFPI,false);}else{co.moveTo(x+w-r,y+h-r);co.fillRect(x+w-r,y+h-r,r,r);}
if(corner_bl){co.moveTo(x+r,y+h-r);co.arc(x+r,y-r+h,r,HALFPI,PI,false);}else{co.moveTo(x,y+h-r);co.fillRect(x,y+h-r,r,r);}
co.fillRect(x+r,y,w-r-r,h);co.fillRect(x,y+r,r+1,h-r-r);co.fillRect(x+w-r-1,y+r,r+1,h-r-r);co.fill();}
RGraph.HideZoomedCanvas=function()
{var interval=15;var frames=10;if(typeof(__zoomedimage__)=='object'){var obj=__zoomedimage__.obj;var prop=obj.properties;}else{return;}
if(prop['chart.zoom.fade.out']){for(var i=frames,j=1;i>=0;--i,++j){if(typeof(__zoomedimage__)=='object'){setTimeout("__zoomedimage__.style.opacity = "+String(i/10),j*interval);}}
if(typeof(__zoomedbackground__)=='object'){setTimeout("__zoomedbackground__.style.opacity = "+String(i/frames),j*interval);}}
if(typeof(__zoomedimage__)=='object'){setTimeout("__zoomedimage__.style.display = 'none'",prop['chart.zoom.fade.out']?(frames*interval)+10:0);}
if(typeof(__zoomedbackground__)=='object'){setTimeout("__zoomedbackground__.style.display = 'none'",prop['chart.zoom.fade.out']?(frames*interval)+10:0);}}
RGraph.AddCustomEventListener=function(obj,name,func)
{var RG=RGraph;if(typeof(RG.events[obj.uid])=='undefined'){RG.events[obj.uid]=[];}
RG.events[obj.uid].push([obj,name,func]);return RG.events[obj.uid].length-1;}
RGraph.FireCustomEvent=function(obj,name)
{var RG=RGraph;if(obj&&obj.isRGraph){if(obj[name]){(obj[name])(obj);}
var uid=obj.uid;if(typeof(uid)=='string'&&typeof(RG.events)=='object'&&typeof(RG.events[uid])=='object'&&RG.events[uid].length>0){for(var j=0;j<RG.events[uid].length;++j){if(RG.events[uid][j]&&RG.events[uid][j][1]==name){RG.events[uid][j][2](obj);}}}}}
RGraph.SetConfig=function(obj,config)
{for(i in config){if(typeof(i)=='string'){obj.Set(i,config[i]);}}
return obj;}
RGraph.RemoveAllCustomEventListeners=function()
{var RG=RGraph;var id=arguments[0];if(id&&RG.events[id]){RG.events[id]=[];}else{RG.events=[];}}
RGraph.RemoveCustomEventListener=function(obj,i)
{var RG=RGraph;if(typeof(RG.events)=='object'&&typeof(RG.events[obj.id])=='object'&&typeof(RG.events[obj.id][i])=='object'){RG.events[obj.id][i]=null;}}
RGraph.DrawBackgroundImage=function(obj)
{var prop=obj.properties;var ca=obj.canvas;var co=obj.context;var RG=RGraph;if(typeof(prop['chart.background.image'])=='string'){if(typeof(ca.__rgraph_background_image__)=='undefined'){var img=new Image();img.__object__=obj;img.__canvas__=ca;img.__context__=co;img.src=obj.Get('chart.background.image');ca.__rgraph_background_image__=img;}else{img=ca.__rgraph_background_image__;}
img.onload=function()
{obj.__rgraph_background_image_loaded__=true;RG.Clear(ca);RG.RedrawCanvas(ca);}
var gutterLeft=obj.gutterLeft;var gutterRight=obj.gutterRight;var gutterTop=obj.gutterTop;var gutterBottom=obj.gutterBottom;var stretch=prop['chart.background.image.stretch'];var align=prop['chart.background.image.align'];if(typeof(align)=='string'){if(align.indexOf('right')!=-1){var x=ca.width-img.width-gutterRight;}else{var x=gutterLeft;}
if(align.indexOf('bottom')!=-1){var y=ca.height-img.height-gutterBottom;}else{var y=gutterTop;}}else{var x=gutterLeft;var y=gutterTop;}
var x=typeof(prop['chart.background.image.x'])=='number'?prop['chart.background.image.x']:x;var y=typeof(prop['chart.background.image.y'])=='number'?prop['chart.background.image.y']:y;var w=stretch?ca.width-gutterLeft-gutterRight:img.width;var h=stretch?ca.height-gutterTop-gutterBottom:img.height;if(typeof(prop['chart.background.image.w'])=='number')w=prop['chart.background.image.w'];if(typeof(prop['chart.background.image.h'])=='number')h=prop['chart.background.image.h'];co.drawImage(img,x,y,w,h);}}
RGraph.hasTooltips=function(obj)
{var prop=obj.properties;if(typeof(prop['chart.tooltips'])=='object'&&prop['chart.tooltips']){for(var i=0,len=prop['chart.tooltips'].length;i<len;++i){if(!RGraph.is_null(obj.Get('chart.tooltips')[i])){return true;}}}else if(typeof(prop['chart.tooltips'])=='function'){return true;}
return false;}
RGraph.CreateUID=function()
{return'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,function(c)
{var r=Math.random()*16|0,v=c=='x'?r:(r&0x3|0x8);return v.toString(16);});}
RGraph.ObjectRegistry.Add=function(obj)
{var uid=obj.uid;var id=obj.canvas.id;var RG=RGraph;RG.ObjectRegistry.objects.byUID.push([uid,obj]);RG.ObjectRegistry.objects.byCanvasID.push([id,obj]);}
RGraph.ObjectRegistry.Remove=function(obj)
{var id=obj.id;var uid=obj.uid;var RG=RGraph;for(var i=0;i<RG.ObjectRegistry.objects.byUID.length;++i){if(RG.ObjectRegistry.objects.byUID[i]&&RG.ObjectRegistry.objects.byUID[i][1].uid==uid){RG.ObjectRegistry.objects.byUID[i]=null;}}
for(var i=0;i<RG.ObjectRegistry.objects.byCanvasID.length;++i){if(RG.ObjectRegistry.objects.byCanvasID[i]&&RG.ObjectRegistry.objects.byCanvasID[i][1]&&RG.ObjectRegistry.objects.byCanvasID[i][1].uid==uid){RG.ObjectRegistry.objects.byCanvasID[i]=null;}}}
RGraph.ObjectRegistry.Clear=function()
{var RG=RGraph;if(arguments[0]){var id=(typeof(arguments[0])=='object'?arguments[0].id:arguments[0]);var objects=RG.ObjectRegistry.getObjectsByCanvasID(id);for(var i=0;i<objects.length;++i){RG.ObjectRegistry.Remove(objects[i]);}}else{RG.ObjectRegistry.objects={};RG.ObjectRegistry.objects.byUID=[];RG.ObjectRegistry.objects.byCanvasID=[];}}
RGraph.ObjectRegistry.List=function()
{var list=[];var RG=RGraph;for(var i=0,len=RG.ObjectRegistry.objects.byUID.length;i<len;++i){if(RG.ObjectRegistry.objects.byUID[i]){list.push(RG.ObjectRegistry.objects.byUID[i][1].type);}}
if(arguments[0]){return list;}else{p(list);}}
RGraph.ObjectRegistry.ClearByType=function(type)
{var RG=RGraph;var objects=RG.ObjectRegistry.objects.byUID;for(var i=0;i<objects.length;++i){if(objects[i]){var uid=objects[i][0];var obj=objects[i][1];if(obj&&obj.type==type){RG.ObjectRegistry.Remove(obj);}}}}
RGraph.ObjectRegistry.Iterate=function(func)
{var objects=RGraph.ObjectRegistry.objects.byUID;for(var i=0;i<objects.length;++i){if(typeof arguments[1]=='string'){var types=arguments[1].split(/,/);for(var j=0;j<types.length;++j){if(types[j]==objects[i][1].type){func(objects[i][1]);}}}else{func(objects[i][1]);}}}
RGraph.ObjectRegistry.getObjectsByCanvasID=function(id)
{var store=RGraph.ObjectRegistry.objects.byCanvasID;var ret=[];for(var i=0;i<store.length;++i){if(store[i]&&store[i][0]==id){ret.push(store[i][1]);}}
return ret;}
RGraph.ObjectRegistry.getFirstObjectByXY=RGraph.ObjectRegistry.getObjectByXY=function(e)
{var canvas=e.target;var ret=null;var objects=RGraph.ObjectRegistry.getObjectsByCanvasID(canvas.id);for(var i=(objects.length-1);i>=0;--i){var obj=objects[i].getObjectByXY(e);if(obj){return obj;}}}
RGraph.ObjectRegistry.getObjectsByXY=function(e)
{var canvas=e.target;var ret=[];var objects=RGraph.ObjectRegistry.getObjectsByCanvasID(canvas.id);for(var i=(objects.length-1);i>=0;--i){var obj=objects[i].getObjectByXY(e);if(obj){ret.push(obj);}}
return ret;}
RGraph.ObjectRegistry.getObjectByUID=function(uid)
{var objects=RGraph.ObjectRegistry.objects.byUID;for(var i=0;i<objects.length;++i){if(objects[i]&&objects[i][1].uid==uid){return objects[i][1];}}}
RGraph.ObjectRegistry.bringToFront=function(obj)
{var redraw=typeof arguments[1]=='undefined'?true:arguments[1];RGraph.ObjectRegistry.Remove(obj);RGraph.ObjectRegistry.Add(obj);if(redraw){RGraph.RedrawCanvas(obj.canvas);}}
RGraph.ObjectRegistry.getObjectsByType=function(type)
{var objects=RGraph.ObjectRegistry.objects.byUID;var ret=[];for(var i=0;i<objects.length;++i){if(objects[i]&&objects[i][1]&&objects[i][1].type&&objects[i][1].type&&objects[i][1].type==type){ret.push(objects[i][1]);}}
return ret;}
RGraph.ObjectRegistry.getFirstObjectByType=function(type)
{var objects=RGraph.ObjectRegistry.objects.byUID;for(var i=0;i<objects.length;++i){if(objects[i]&&objects[i][1]&&objects[i][1].type==type){return objects[i][1];}}
return null;}
RGraph.getAngleByXY=function(cx,cy,x,y)
{var angle=Math.atan((y-cy)/(x-cx));angle=Math.abs(angle)
if(x>=cx&&y>=cy){angle+=TWOPI;}else if(x>=cx&&y<cy){angle=(HALFPI-angle)+(PI+HALFPI);}else if(x<cx&&y<cy){angle+=PI;}else{angle=PI-angle;}
if(angle>TWOPI){angle-=TWOPI;}
return angle;}
RGraph.getHypLength=function(x1,y1,x2,y2)
{var ret=Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));return ret;}
RGraph.getRadiusEndPoint=function(cx,cy,angle,radius)
{var x=cx+(Math.cos(angle)*radius);var y=cy+(Math.sin(angle)*radius);return[x,y];}
RGraph.InstallEventListeners=function(obj)
{var RG=RGraph;var prop=obj.properties;if(ISOLD){return;}
if(RG.InstallCanvasClickListener){RG.InstallWindowMousedownListener(obj);RG.InstallWindowMouseupListener(obj);RG.InstallCanvasMousemoveListener(obj);RG.InstallCanvasMouseupListener(obj);RG.InstallCanvasMousedownListener(obj);RG.InstallCanvasClickListener(obj);}else if(RG.hasTooltips(obj)||prop['chart.adjustable']||prop['chart.annotatable']||prop['chart.contextmenu']||prop['chart.resizable']||prop['chart.key.interactive']||prop['chart.events.click']||prop['chart.events.mousemove']||typeof obj.onclick=='function'||typeof obj.onmousemove=='function'){alert('[RGRAPH] You appear to have used dynamic features but not included the file: RGraph.common.dynamic.js');}}
RGraph.pr=function(obj)
{var indent=(arguments[2]?arguments[2]:'    ');var str='';var counter=typeof arguments[3]=='number'?arguments[3]:0;if(counter>=5){return'';}
switch(typeof obj){case'string':str+=obj+' ('+(typeof obj)+', '+obj.length+')';break;case'number':str+=obj+' ('+(typeof obj)+')';break;case'boolean':str+=obj+' ('+(typeof obj)+')';break;case'function':str+='function () {}';break;case'undefined':str+='undefined';break;case'null':str+='null';break;case'object':if(RGraph.is_null(obj)){str+=indent+'null\n';}else{str+=indent+'Object {'+'\n'
for(j in obj){str+=indent+'    '+j+' => '+RGraph.pr(obj[j],true,indent+'    ',counter+1)+'\n';}
str+=indent+'}';}
break;default:str+='Unknown type: '+typeof obj+'';break;}
if(!arguments[1]){alert(str);}
return str;}
RGraph.DashedLine=function(co,x1,y1,x2,y2)
{var size=5;if(typeof(arguments[5])=='number'){size=arguments[5];}
var dx=x2-x1;var dy=y2-y1;var num=Math.floor(Math.sqrt((dx*dx)+(dy*dy))/size);var xLen=dx/num;var yLen=dy/num;var count=0;do{(count%2==0&&count>0)?co.lineTo(x1,y1):co.moveTo(x1,y1);x1+=xLen;y1+=yLen;}while(count++<=num);}
RGraph.AJAX=function(url,callback)
{if(window.XMLHttpRequest){var httpRequest=new XMLHttpRequest();}else if(window.ActiveXObject){var httpRequest=new ActiveXObject("Microsoft.XMLHTTP");}
httpRequest.onreadystatechange=function()
{if(this.readyState==4&&this.status==200){this.__user_callback__=callback;this.__user_callback__(this.responseText);}}
httpRequest.open('GET',url,true);httpRequest.send();}
RGraph.AJAX.POST=function(url,data,callback)
{var crumbs=[];if(window.XMLHttpRequest){var httpRequest=new XMLHttpRequest();}else if(window.ActiveXObject){var httpRequest=new ActiveXObject("Microsoft.XMLHTTP");}
httpRequest.onreadystatechange=function()
{if(this.readyState==4&&this.status==200){this.__user_callback__=callback;this.__user_callback__(this.responseText);}}
httpRequest.open('POST',url,true);httpRequest.setRequestHeader("Content-type","application/x-www-form-urlencoded");for(i in data){if(typeof i=='string'){crumbs.push(i+'='+encodeURIComponent(data[i]));}}
httpRequest.send(crumbs.join('&'));}
RGraph.AJAX.getNumber=function(url,callback)
{RGraph.AJAX(url,function()
{var num=parseFloat(this.responseText);callback(num);});}
RGraph.AJAX.getString=function(url,callback)
{RGraph.AJAX(url,function()
{var str=String(this.responseText);callback(str);});}
RGraph.AJAX.getJSON=function(url,callback)
{RGraph.AJAX(url,function()
{var json=eval('('+this.responseText+')');callback(json);});}
RGraph.AJAX.getCSV=function(url,callback)
{var seperator=arguments[2]?arguments[2]:',';RGraph.AJAX(url,function()
{var regexp=new RegExp(seperator);var arr=this.responseText.split(regexp);for(var i=0,len=arr.length;i<len;++i){arr[i]=parseFloat(arr[i]);}
callback(arr);});}
RGraph.RotateCanvas=function(ca,x,y,angle)
{var co=ca.getContext('2d');co.translate(x,y);co.rotate(angle);co.translate(0-x,0-y);}
RGraph.MeasureText=function(text,bold,font,size)
{if(typeof(__rgraph_measuretext_cache__)=='undefined'){__rgraph_measuretext_cache__=[];}
var str=text+':'+bold+':'+font+':'+size;if(typeof(__rgraph_measuretext_cache__)=='object'&&__rgraph_measuretext_cache__[str]){return __rgraph_measuretext_cache__[str];}
if(!__rgraph_measuretext_cache__['text-div']){var div=document.createElement('DIV');div.style.position='absolute';div.style.top='-100px';div.style.left='-100px';document.body.appendChild(div);__rgraph_measuretext_cache__['text-div']=div;}else if(__rgraph_measuretext_cache__['text-div']){var div=__rgraph_measuretext_cache__['text-div'];}
div.innerHTML=text.replace(/\r\n/g,'<br />');div.style.fontFamily=font;div.style.fontWeight=bold?'bold':'normal';div.style.fontSize=size+'pt';var size=[div.offsetWidth,div.offsetHeight];__rgraph_measuretext_cache__[str]=size;return size;}
RGraph.Text2=function(obj,opt)
{if(obj&&obj.isRGraph){var co=obj.context;var ca=obj.canvas;}else if(typeof obj=='string'){var ca=document.getElementById(obj);var co=ca.getContext('2d');}else if(typeof obj.getContext=='function'){var ca=obj;var co=ca.getContext('2d');}else if(obj.toString().indexOf('CanvasRenderingContext2D')!=-1){var co=obj;var ca=obj.context;}
var x=opt.x;var y=opt.y;var originalX=x;var originalY=y;var text=opt.text;var text_multiline=text.split(/\r?\n/g);var numlines=text_multiline.length;var font=opt.font?opt.font:'Arial';var size=opt.size?opt.size:10;var size_pixels=size*1.5;var bold=opt.bold;var halign=opt.halign?opt.halign:'left';var valign=opt.valign?opt.valign:'bottom';var tag=typeof opt.tag=='string'&&opt.tag.length>0?opt.tag:'';var marker=opt.marker;var angle=opt.angle||0;if(typeof opt.boundingFill=='string')opt['bounding.fill']=opt.boundingFill;if(typeof opt.boundingStroke=='string')opt['bounding.stroke']=opt.boundingStroke;var bounding=opt.bounding;var bounding_stroke=opt['bounding.stroke']?opt['bounding.stroke']:'black';var bounding_fill=opt['bounding.fill']?opt['bounding.fill']:'rgba(255,255,255,0.7)';var bounding_shadow=opt['bounding.shadow'];var bounding_shadow_color=opt['bounding.shadow.color']||'#ccc';var bounding_shadow_blur=opt['bounding.shadow.blur']||3;var bounding_shadow_offsetx=opt['bounding.shadow.offsetx']||3;var bounding_shadow_offsety=opt['bounding.shadow.offsety']||3;var bounding_linewidth=opt['bounding.linewidth']||1;var ret={};if(typeof text=='number'){text=String(text);}
if(typeof text!='string'){alert('[RGRAPH TEXT] The text given must a string or a number');return;}
if(angle!=0){co.save();co.translate(x,y);co.rotate((Math.PI/180)*angle)
x=0;y=0;}
co.font=(opt.bold?'bold ':'')+size+'pt '+font;var width=0;for(var i=0;i<numlines;++i){width=Math.max(width,co.measureText(text_multiline[i]).width);}
var height=size_pixels*numlines;if(opt.marker){var marker_size=10;var strokestyle=co.strokeStyle;co.beginPath();co.strokeStyle='red';co.moveTo(x,y-marker_size);co.lineTo(x,y+marker_size);co.moveTo(x-marker_size,y);co.lineTo(x+marker_size,y);co.stroke();co.strokeStyle=strokestyle;}
if(halign=='center'){co.textAlign='center';var boundingX=x-2-(width/2);}else if(halign=='right'){co.textAlign='right';var boundingX=x-2-width;}else{co.textAlign='left';var boundingX=x-2;}
if(valign=='center'){co.textBaseline='middle';y-=1;y-=((numlines-1)/2)*size_pixels;var boundingY=y-(size_pixels/2)-2;}else if(valign=='top'){co.textBaseline='top';var boundingY=y-2;}else{co.textBaseline='bottom';if(numlines>1){y-=((numlines-1)*size_pixels);}
var boundingY=y-size_pixels-2;}
var boundingW=width+4;var boundingH=height+4;if(bounding){var pre_bounding_linewidth=co.lineWidth;var pre_bounding_strokestyle=co.strokeStyle;var pre_bounding_fillstyle=co.fillStyle;var pre_bounding_shadowcolor=co.shadowColor;var pre_bounding_shadowblur=co.shadowBlur;var pre_bounding_shadowoffsetx=co.shadowOffsetX;var pre_bounding_shadowoffsety=co.shadowOffsetY;co.lineWidth=bounding_linewidth;co.strokeStyle=bounding_stroke;co.fillStyle=bounding_fill;if(bounding_shadow){co.shadowColor=bounding_shadow_color;co.shadowBlur=bounding_shadow_blur;co.shadowOffsetX=bounding_shadow_offsetx;co.shadowOffsetY=bounding_shadow_offsety;}
co.strokeRect(boundingX,boundingY,boundingW,boundingH);co.fillRect(boundingX,boundingY,boundingW,boundingH);co.lineWidth=pre_bounding_linewidth;co.strokeStyle=pre_bounding_strokestyle;co.fillStyle=pre_bounding_fillstyle;co.shadowColor=pre_bounding_shadowcolor
co.shadowBlur=pre_bounding_shadowblur
co.shadowOffsetX=pre_bounding_shadowoffsetx
co.shadowOffsetY=pre_bounding_shadowoffsety}
if(numlines>1){for(var i=0;i<numlines;++i){co.fillText(text_multiline[i],x,y+(size_pixels*i));}}else{co.fillText(text,x,y);}
if(angle!=0){if(angle==90){if(halign=='left'){if(valign=='bottom'){boundingX=originalX-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-height-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}}else if(halign=='center'){if(valign=='bottom'){boundingX=originalX-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-height-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}}else if(halign=='right'){if(valign=='bottom'){boundingX=originalX-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-height-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}}}else if(angle==180){if(halign=='left'){if(valign=='bottom'){boundingX=originalX-width-2;boundingY=originalY-2;boundingW=width+4;boundingH=height+4;}
if(valign=='center'){boundingX=originalX-width-2;boundingY=originalY-(height/2)-2;boundingW=width+4;boundingH=height+4;}
if(valign=='top'){boundingX=originalX-width-2;boundingY=originalY-height-2;boundingW=width+4;boundingH=height+4;}}else if(halign=='center'){if(valign=='bottom'){boundingX=originalX-(width/2)-2;boundingY=originalY-2;boundingW=width+4;boundingH=height+4;}
if(valign=='center'){boundingX=originalX-(width/2)-2;boundingY=originalY-(height/2)-2;boundingW=width+4;boundingH=height+4;}
if(valign=='top'){boundingX=originalX-(width/2)-2;boundingY=originalY-height-2;boundingW=width+4;boundingH=height+4;}}else if(halign=='right'){if(valign=='bottom'){boundingX=originalX-2;boundingY=originalY-2;boundingW=width+4;boundingH=height+4;}
if(valign=='center'){boundingX=originalX-2;boundingY=originalY-(height/2)-2;boundingW=width+4;boundingH=height+4;}
if(valign=='top'){boundingX=originalX-2;boundingY=originalY-height-2;boundingW=width+4;boundingH=height+4;}}}else if(angle==270){if(halign=='left'){if(valign=='bottom'){boundingX=originalX-height-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-4;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-2;boundingY=originalY-width-2;boundingW=height+4;boundingH=width+4;}}else if(halign=='center'){if(valign=='bottom'){boundingX=originalX-height-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-4;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-2;boundingY=originalY-(width/2)-2;boundingW=height+4;boundingH=width+4;}}else if(halign=='right'){if(valign=='bottom'){boundingX=originalX-height-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}
if(valign=='center'){boundingX=originalX-(height/2)-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}
if(valign=='top'){boundingX=originalX-2;boundingY=originalY-2;boundingW=height+4;boundingH=width+4;}}}
co.restore();}
co.textBaseline='alphabetic';co.textAlign='left';ret.x=boundingX;ret.y=boundingY;ret.width=boundingW;ret.height=boundingH
ret.object=obj;ret.text=text;ret.tag=tag;if(obj&&obj.isRGraph&&obj.coordsText){obj.coordsText.push(ret);}
return ret;}
RGraph.sequentialIndexToGrouped=function(index,data)
{var group=0;var grouped_index=0;while(--index>=0){if(RGraph.is_null(data[group])){group++;grouped_index=0;continue;}
if(typeof data[group]=='number'){group++
grouped_index=0;continue;}
grouped_index++;if(grouped_index>=data[group].length){group++;grouped_index=0;}}
return[group,grouped_index];}
if(!Array.prototype.forEach){Array.prototype.forEach=function(fn,scope){for(var i=0,len=this.length;i<len;++i){fn.call(scope,this[i],i,this);}}}
function empty(value)
{if(!value||value.length<=0){return true;}
return false;}
RGraph.Highlight.Rect=function(obj,shape)
{var ca=obj.canvas;var co=obj.context;var prop=obj.properties;if(prop['chart.tooltips.highlight']){co.lineWidth=1;co.beginPath();co.strokeStyle=prop['chart.highlight.stroke'];co.fillStyle=prop['chart.highlight.fill'];co.strokeRect(shape['x'],shape['y'],shape['width'],shape['height']);co.fillRect(shape['x'],shape['y'],shape['width'],shape['height']);co.stroke;co.fill();}}
RGraph.Highlight.Point=function(obj,shape)
{var prop=obj.properties;var ca=obj.canvas;var co=obj.context;if(prop['chart.tooltips.highlight']){co.beginPath();co.strokeStyle=prop['chart.highlight.stroke'];co.fillStyle=prop['chart.highlight.fill'];var radius=prop['chart.highlight.point.radius']||2;co.arc(shape['x'],shape['y'],radius,0,TWOPI,0);co.stroke();co.fill();}}
RGraph.LinearGradient=function(obj,x1,y1,x2,y2,color1,color2){var gradient=obj.context.createLinearGradient(x1,y1,x2,y2);var numColors=arguments.length-5;for(var i=5;i<arguments.length;++i){var color=arguments[i];var stop=(i-5)/(numColors-1);gradient.addColorStop(stop,color);}return gradient;}
RGraph.RadialGradient=function(obj,x1,y1,r1,x2,y2,r2,color1,color2){var gradient=obj.context.createRadialGradient(x1,y1,r1,x2,y2,r2);var numColors=arguments.length-7;for(var i=7;i<arguments.length;++i){var color=arguments[i];var stop=(i-7)/(numColors-1);gradient.addColorStop(stop,color);}return gradient;}
RGraph.array_shift=function(arr){var ret=[];for(var i=1;i<arr.length;++i){ret.push(arr[i]);}return ret;}
RGraph.AddEventListener=function(id,e,func){var type=arguments[3]?arguments[3]:'unknown';RGraph.Registry.Get('chart.event.handlers').push([id,e,func,type]);}
RGraph.ClearEventListeners=function(id){if(id&&id=='window'){window.removeEventListener('mousedown',window.__rgraph_mousedown_event_listener_installed__,false);window.removeEventListener('mouseup',window.__rgraph_mouseup_event_listener_installed__,false);}else{var canvas=document.getElementById(id);canvas.removeEventListener('mouseup',canvas.__rgraph_mouseup_event_listener_installed__,false);canvas.removeEventListener('mousemove',canvas.__rgraph_mousemove_event_listener_installed__,false);canvas.removeEventListener('mousedown',canvas.__rgraph_mousedown_event_listener_installed__,false);canvas.removeEventListener('click',canvas.__rgraph_click_event_listener_installed__,false);}}
RGraph.HidePalette=function(){var div=RGraph.Registry.Get('palette');if(typeof(div)=='object'&&div){div.style.visibility='hidden';div.style.display='none';RGraph.Registry.Set('palette',null);}}
RGraph.random=function(min,max){var dp=arguments[2]?arguments[2]:0;var r=Math.random();return Number((((max-min)*r)+min).toFixed(dp));}
RGraph.NoShadow=function(obj){obj.context.shadowColor='rgba(0,0,0,0)';obj.context.shadowBlur=0;obj.context.shadowOffsetX=0;obj.context.shadowOffsetY=0;}
RGraph.SetShadow=function(obj,color,offsetx,offsety,blur){obj.context.shadowColor=color;obj.context.shadowOffsetX=offsetx;obj.context.shadowOffsetY=offsety;obj.context.shadowBlur=blur;}
RGraph.array_reverse=function(arr){var newarr=[];for(var i=arr.length-1;i>=0;i--){newarr.push(arr[i]);}return newarr;}
RGraph.Registry.Set=function(name,value){RGraph.Registry.store[name]=value;return value;}
RGraph.Registry.Get=function(name){return RGraph.Registry.store[name];}
RGraph.degrees2Radians=function(degrees){return degrees*(PI/180);}
RGraph.log=(function(n,base){var log=Math.log;return function(n,base){return log(n)/(base?log(base):1);};})();RGraph.is_array=function(obj){return obj!=null&&obj.constructor.toString().indexOf('Array')!=-1;}
RGraph.trim=function(str){return RGraph.ltrim(RGraph.rtrim(str));}
RGraph.ltrim=function(str){return str.replace(/^(\s|\0)+/,'');}
RGraph.rtrim=function(str){return str.replace(/(\s|\0)+$/,'');}
RGraph.GetHeight=function(obj){return obj.canvas.height;}
RGraph.GetWidth=function(obj){return obj.canvas.width;}
RGraph.is_null=function(arg){if(arg==null||(typeof(arg))=='object'&&!arg){return true;}return false;}
RGraph.Timer=function(label){if(typeof(RGraph.TIMER_LAST_CHECKPOINT)=='undefined'){RGraph.TIMER_LAST_CHECKPOINT=Date.now();}var now=Date.now();console.log(label+': '+(now-RGraph.TIMER_LAST_CHECKPOINT).toString());RGraph.TIMER_LAST_CHECKPOINT=now;}
RGraph.Async=function(func){return setTimeout(func,arguments[1]?arguments[1]:1);}
RGraph.isIE=function(){return navigator.userAgent.indexOf('MSIE')>0;};ISIE=RGraph.isIE();RGraph.isIE6=function(){return navigator.userAgent.indexOf('MSIE 6')>0;};ISIE6=RGraph.isIE6();RGraph.isIE7=function(){return navigator.userAgent.indexOf('MSIE 7')>0;};ISIE7=RGraph.isIE7();RGraph.isIE8=function(){return navigator.userAgent.indexOf('MSIE 8')>0;};ISIE8=RGraph.isIE8();RGraph.isIE9=function(){return navigator.userAgent.indexOf('MSIE 9')>0;};ISIE9=RGraph.isIE9();RGraph.isIE10=function(){return navigator.userAgent.indexOf('MSIE 10')>0;};ISIE10=RGraph.isIE10();RGraph.isIE9up=function(){navigator.userAgent.match(/MSIE (\d+)/);return Number(RegExp.$1)>=9;};ISIE9UP=RGraph.isIE9up();RGraph.isIE10up=function(){navigator.userAgent.match(/MSIE (\d+)/);return Number(RegExp.$1)>=10;};ISIE10UP=RGraph.isIE10up();RGraph.isOld=function(){return ISIE6||ISIE7||ISIE8;};ISOLD=RGraph.isOld();RGraph.Reset=function(canvas){canvas.width=canvas.width;RGraph.ObjectRegistry.Clear(canvas);canvas.__rgraph_aa_translated__=false;}
function pd(variable){RGraph.pr(variable);}
function p(variable){RGraph.pr(arguments[0],arguments[1],arguments[3]);}
function a(variable){alert(variable);}
function cl(variable){return console.log(variable);}


if(typeof(RGraph)=='undefined')RGraph={isRGraph:true,type:'common'};RGraph.Effects={};RGraph.Effects.Fade={};RGraph.Effects.jQuery={}
RGraph.Effects.jQuery.HBlinds={};RGraph.Effects.jQuery.VBlinds={}
RGraph.Effects.jQuery.Slide={};RGraph.Effects.Pie={}
RGraph.Effects.Bar={};RGraph.Effects.Line={}
RGraph.Effects.Line.jQuery={};RGraph.Effects.Fuel={}
RGraph.Effects.Rose={};RGraph.Effects.Odo={}
RGraph.Effects.Gauge={};RGraph.Effects.Meter={}
RGraph.Effects.HBar={};RGraph.Effects.HProgress={}
RGraph.Effects.VProgress={};RGraph.Effects.Radar={}
RGraph.Effects.Waterfall={};RGraph.Effects.Gantt={}
RGraph.Effects.Thermometer={};RGraph.Effects.Scatter={}
RGraph.Effects.Scatter.jQuery={};RGraph.Effects.CornerGauge={}
RGraph.Effects.jQuery.HScissors={};RGraph.Effects.jQuery.VScissors={}
RGraph.Effects.Fade.In=function(obj)
{var canvas=obj.canvas;var duration=(arguments[1]&&arguments[1].duration?arguments[1].duration:250);var frames=(arguments[1]&&arguments[1].frames?arguments[1].frames:5);canvas.style.opacity=0;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);for(var i=1;i<=frames;++i){setTimeout('document.getElementById("'+canvas.id+'").style.opacity = '+(i*(1/frames)),i*(duration/frames));}
if(typeof(arguments[2])=='function'){setTimeout(arguments[2],duration);}}
RGraph.Effects.Fade.Out=function(obj)
{var canvas=obj.canvas;var duration=(arguments[1]&&arguments[1].duration?arguments[1].duration:250);var frames=(arguments[1]&&arguments[1].frames?arguments[1].frames:5);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);for(var i=frames;i>=0;--i){setTimeout('document.getElementById("'+canvas.id+'").style.opacity = '+(i*(1/frames)),(frames-i)*(duration/frames));}
if(typeof(arguments[2])=='function'){setTimeout(arguments[2],duration);}}
RGraph.Effects.jQuery.Expand=function(obj)
{if(typeof(jQuery)=='undefined'){alert('[ERROR] Could not find jQuery object - have you included the jQuery file?');}
var bounce=(!arguments[1]||(arguments[1]&&(arguments[1].bounce||typeof(arguments[1].bounce)=='undefined')))?true:false;var canvas=obj.canvas;if(!canvas.__rgraph_div_placeholder__){var div=RGraph.Effects.ReplaceCanvasWithDIV(canvas);canvas.__rgraph_div_placeholder__=div;}else{div=canvas.__rgraph_div_placeholder__;}
div.style.position='relative';canvas.style.position='absolute';canvas.style.top=(canvas.height/2)+'px';canvas.style.left=(canvas.width/2)+'px';canvas.style.width=0;canvas.style.height=0;canvas.style.opacity=0;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(bounce){jQuery('#'+obj.id).animate({opacity:1,width:'120%',height:'120%',left:(canvas.width*-0.1)+'px',top:(canvas.height*-0.1)+'px'},500,function(){jQuery('#'+obj.id).animate({width:'90%',height:'90%',top:(canvas.height*0.05)+'px',left:(canvas.width*0.05)+'px'},250,function()
{jQuery('#'+obj.id).animate({width:'101%',height:'101%',top:(canvas.height*-0.005)+'px',left:(canvas.width*-0.005)+'px'},250,function()
{jQuery('#'+obj.id).animate({width:'100%',height:'100%',top:0,left:0},250);});});});}else{jQuery('#'+obj.id).animate({opacity:1,width:'100%',height:'100%',left:0,top:0},1000)}
if(typeof(arguments[2])=='function'){setTimeout(arguments[2],1000);}}
RGraph.Effects.jQuery.Contract=function(obj)
{if(typeof(jQuery)=='undefined'){alert('[ERROR] Could not find jQuery object - have you included the jQuery file?');}
var canvas=obj.canvas;if(!canvas.__rgraph_div_placeholder__){var div=RGraph.Effects.ReplaceCanvasWithDIV(canvas);canvas.__rgraph_div_placeholder__=div;}else{div=canvas.__rgraph_div_placeholder__;}
div.style.position='relative';canvas.style.position='absolute';canvas.style.top=0;canvas.style.left=0;jQuery('#'+obj.id).animate({width:(canvas.width*1.2)+'px',height:(canvas.height*1.2)+'px',left:(canvas.width*-0.1)+'px',top:(canvas.height*-0.1)+'px'},250,function()
{jQuery('#'+obj.id).animate({opacity:0,width:0,height:0,left:(canvas.width*0.5)+'px',top:(canvas.height*0.5)+'px'},750)});if(typeof(arguments[2])=='function'){setTimeout(arguments[2],1000);}}
RGraph.Effects.ReplaceCanvasWithDIV=function(canvas)
{if(!canvas.replacementDIV){var div=document.createElement('DIV');div.style.width=canvas.width+'px';div.style.height=canvas.height+'px';div.style.cssFloat=canvas.style.cssFloat;div.style.left=canvas.style.left;div.style.top=canvas.style.top;div.style.display='inline-block';canvas.parentNode.insertBefore(div,canvas);canvas.parentNode.removeChild(canvas);div.appendChild(canvas);canvas.style.position='relative';canvas.style.left=(div.offsetWidth/2)+'px';canvas.style.top=(div.offsetHeight/2)+'px';canvas.style.cssFloat='';canvas.replacementDIV=div;}else{var div=canvas.replacementDIV;}
return div;}
RGraph.Effects.jQuery.Snap=function(obj)
{var delay=500;var div=RGraph.Effects.ReplaceCanvasWithDIV(obj.canvas);obj.canvas.style.position='absolute';obj.canvas.style.top=0;obj.canvas.style.left=0;obj.canvas.style.width=0;obj.canvas.style.height=0;obj.canvas.style.opacity=0;var targetLeft=div.offsetLeft;var targetTop=div.offsetTop;var targetWidth=div.offsetWidth;var targetHeight=div.offsetHeight;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);jQuery('#'+obj.id).animate({opacity:1,width:targetWidth+'px',height:targetHeight+'px',left:targetLeft+'px',top:targetTop+'px'},delay);if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay+50);}}
RGraph.Effects.jQuery.Reveal=function(obj)
{var opts=arguments[1]?arguments[1]:null;var delay=1000;var canvas=obj.canvas;var xy=RGraph.getCanvasXY(obj.canvas);obj.canvas.style.visibility='hidden';RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);var divs=[['reveal_left',xy[0],xy[1],obj.canvas.width/2,obj.canvas.height],['reveal_right',(xy[0]+(obj.canvas.width/2)),xy[1],(obj.canvas.width/2),obj.canvas.height],['reveal_top',xy[0],xy[1],obj.canvas.width,(obj.canvas.height/2)],['reveal_bottom',xy[0],(xy[1]+(obj.canvas.height/2)),obj.canvas.width,(obj.canvas.height/2)]];for(var i=0;i<divs.length;++i){var div=document.createElement('DIV');div.id=divs[i][0];div.style.width=divs[i][3]+'px';div.style.height=divs[i][4]+'px';div.style.left=divs[i][1]+'px';div.style.top=divs[i][2]+'px';div.style.position='absolute';div.style.backgroundColor=opts&&typeof(opts['color'])=='string'?opts['color']:'white';document.body.appendChild(div);}
obj.canvas.style.visibility='visible';jQuery('#reveal_left').animate({width:0},delay);jQuery('#reveal_right').animate({left:'+='+(obj.canvas.width/2),width:0},delay);jQuery('#reveal_top').animate({height:0},delay);jQuery('#reveal_bottom').animate({top:'+='+(obj.canvas.height/2),height:0},delay);setTimeout(function()
{document.body.removeChild(document.getElementById("reveal_top"))
document.body.removeChild(document.getElementById("reveal_bottom"))
document.body.removeChild(document.getElementById("reveal_left"))
document.body.removeChild(document.getElementById("reveal_right"))},delay);if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.RevealCircular=function(obj)
{var opts=arguments[1]?arguments[1]:null;var callback=arguments[2]?arguments[2]:null;var frames=30;var RG=RGraph;var ca=obj.canvas;var co=obj.context;var ra=0;var cx=ca.width/2;var cy=ca.height/2;var target_ra=Math.max(ca.height,ca.width);function Grow()
{RG.Clear(ca);co.save();co.beginPath();co.arc(cx,cy,ra,0,TWOPI,false);co.clip();obj.Draw();co.restore();if(ra<target_ra){ra+=target_ra/30;RG.Effects.UpdateCanvas(Grow);}else if(typeof(callback)=='function'){callback(obj);}}
Grow();}
RGraph.Effects.jQuery.Conceal=function(obj)
{var opts=arguments[1]?arguments[1]:null;var delay=1000;var canvas=obj.canvas;var xy=RGraph.getCanvasXY(obj.canvas);var divs=[['conceal_left',xy[0],xy[1],0,obj.canvas.height],['conceal_right',(xy[0]+obj.canvas.width),xy[1],0,obj.canvas.height],['conceal_top',xy[0],xy[1],obj.canvas.width,0],['conceal_bottom',xy[0],(xy[1]+obj.canvas.height),obj.canvas.width,0]];for(var i=0;i<divs.length;++i){var div=document.createElement('DIV');div.id=divs[i][0];div.style.width=divs[i][3]+'px';div.style.height=divs[i][4]+'px';div.style.left=divs[i][1]+'px';div.style.top=divs[i][2]+'px';div.style.position='absolute';div.style.backgroundColor=opts&&typeof(opts['color'])=='string'?opts['color']:'white';document.body.appendChild(div);}
jQuery('#conceal_left').animate({width:'+='+(obj.canvas.width/2)},delay);jQuery('#conceal_right').animate({left:'-='+(obj.canvas.width/2),width:(obj.canvas.width/2)},delay);jQuery('#conceal_top').animate({height:'+='+(obj.canvas.height/2)},delay);jQuery('#conceal_bottom').animate({top:'-='+(obj.canvas.height/2),height:(obj.canvas.height/2)},delay);setTimeout(function()
{document.body.removeChild(document.getElementById("conceal_top"))
document.body.removeChild(document.getElementById("conceal_bottom"))
document.body.removeChild(document.getElementById("conceal_left"))
document.body.removeChild(document.getElementById("conceal_right"))},delay);setTimeout(function(){RGraph.Clear(obj.canvas);},delay);if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.jQuery.HBlinds.Open=function(obj)
{var canvas=obj.canvas;var opts=arguments[1]?arguments[1]:[];var delay=1000;var color=opts['color']?opts['color']:'white';var xy=RGraph.getCanvasXY(canvas);var height=canvas.height/5;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);for(var i=0;i<5;++i){var div=document.createElement('DIV');div.id='blinds_'+i;div.style.width=canvas.width+'px';div.style.height=height+'px';div.style.left=xy[0]+'px';div.style.top=(xy[1]+(canvas.height*(i/5)))+'px';div.style.position='absolute';div.style.backgroundColor=color;document.body.appendChild(div);jQuery('#blinds_'+i).animate({height:0},delay);}
setTimeout(function(){document.body.removeChild(document.getElementById('blinds_0'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_1'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_2'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_3'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_4'));},delay);if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.jQuery.HBlinds.Close=function(obj)
{var canvas=obj.canvas;var opts=arguments[1]?arguments[1]:[];var delay=1000;var color=opts['color']?opts['color']:'white';var xy=RGraph.getCanvasXY(canvas);var height=canvas.height/5;for(var i=0;i<5;++i){var div=document.createElement('DIV');div.id='blinds_'+i;div.style.width=canvas.width+'px';div.style.height=0;div.style.left=xy[0]+'px';div.style.top=(xy[1]+(canvas.height*(i/5)))+'px';div.style.position='absolute';div.style.backgroundColor=color;document.body.appendChild(div);jQuery('#blinds_'+i).animate({height:height+'px'},delay);}
setTimeout(function(){RGraph.Clear(obj.canvas);},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_0'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_1'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_2'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_3'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_4'));},delay+100);if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.jQuery.VBlinds.Open=function(obj)
{var canvas=obj.canvas;var opts=arguments[1]?arguments[1]:[];var delay=1000;var color=opts['color']?opts['color']:'white';var xy=RGraph.getCanvasXY(canvas);var width=canvas.width/10;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);for(var i=0;i<10;++i){var div=document.createElement('DIV');div.id='blinds_'+i;div.style.width=width+'px';div.style.height=canvas.height+'px';div.style.left=(xy[0]+(canvas.width*(i/10)))+'px';div.style.top=(xy[1])+'px';div.style.position='absolute';div.style.backgroundColor=color;document.body.appendChild(div);jQuery('#blinds_'+i).animate({width:0},delay);}
setTimeout(function(){document.body.removeChild(document.getElementById('blinds_0'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_1'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_2'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_3'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_4'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_5'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_6'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_7'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_8'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_9'));},delay+100);if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.jQuery.VBlinds.Close=function(obj)
{var canvas=obj.canvas;var opts=arguments[1]?arguments[1]:[];var delay=1000;var color=opts['color']?opts['color']:'white';var xy=RGraph.getCanvasXY(canvas);var width=canvas.width/10;for(var i=0;i<10;++i){var div=document.createElement('DIV');div.id='blinds_'+i;div.style.width=0;div.style.height=canvas.height+'px';div.style.left=(xy[0]+(canvas.width*(i/10)))+'px';div.style.top=(xy[1])+'px';div.style.position='absolute';div.style.backgroundColor=color;document.body.appendChild(div);jQuery('#blinds_'+i).animate({width:width},delay);}
setTimeout(function(){RGraph.Clear(obj.canvas,color);},delay+100);if(opts['remove']){setTimeout(function(){document.body.removeChild(document.getElementById('blinds_0'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_1'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_2'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_3'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_4'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_5'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_6'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_7'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_8'));},delay+100);setTimeout(function(){document.body.removeChild(document.getElementById('blinds_9'));},delay+100);}
if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.Pie.Grow=function(obj)
{var canvas=obj.canvas;var opts=arguments[1]?arguments[1]:[];var color=opts['color']?opts['color']:'white';var xy=RGraph.getCanvasXY(canvas);canvas.style.visibility='hidden';RGraph.RedrawCanvas(canvas);var radius=obj.getRadius();if(typeof(obj.Get('chart.radius'))=='number'){radius=obj.Get('chart.radius');}
canvas.style.visibility='visible';obj.Set('chart.radius',0);RGraph.Effects.Animate(obj,{'chart.radius':radius},arguments[2]);}
RGraph.Effects.Bar.Grow=function(obj)
{var callback=arguments[2];obj.original_data=RGraph.array_clone(obj.data);obj.__animation_frame__=0;if(obj.Get('chart.ymax')==null){var ymax=0;for(var i=0;i<obj.data.length;++i){if(RGraph.is_array(obj.data[i])&&obj.Get('chart.grouping')=='stacked'){ymax=Math.max(ymax,Math.abs(RGraph.array_sum(obj.data[i])));}else if(RGraph.is_array(obj.data[i])&&obj.Get('chart.grouping')=='grouped'){ymax=Math.max(ymax,Math.abs(RGraph.array_max(obj.data[i])));}else{ymax=Math.max(ymax,Math.abs(obj.data[i]));}}
var scale=RGraph.getScale2(obj,{'max':ymax});obj.Set('chart.ymax',scale.max);}
function Grow()
{var numFrames=30;if(!obj.__animation_frame__){obj.__animation_frame__=0;obj.__original_hmargin__=obj.Get('chart.hmargin');obj.__hmargin__=((obj.canvas.width-obj.Get('chart.gutter.left')-obj.Get('chart.gutter.right'))/obj.data.length)/2;obj.Set('chart.hmargin',obj.__hmargin__);}
for(var j=0;j<obj.original_data.length;++j){if(typeof(obj.data[j])=='object'){for(var k=0;k<obj.data[j].length;++k){obj.data[j][k]=(obj.__animation_frame__/numFrames)*obj.original_data[j][k];}}else{obj.data[j]=(obj.__animation_frame__/numFrames)*obj.original_data[j];}}
obj.Set('chart.hmargin',((1-(obj.__animation_frame__/numFrames))*(obj.__hmargin__-obj.__original_hmargin__))+obj.__original_hmargin__);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(obj.__animation_frame__<numFrames){obj.__animation_frame__+=1;RGraph.Effects.UpdateCanvas(Grow);}else{if(callback){callback(obj);}}}
RGraph.Effects.UpdateCanvas(Grow);}
RGraph.Effects.UpdateCanvas=function(func)
{window.requestAnimationFrame=window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.msRequestAnimationFrame||window.amozRequestAnimationFrame||(function(func){setTimeout(func,16.666);});window.requestAnimationFrame(func);}
RGraph.Effects.Fuel.Grow=function(obj)
{var callback=arguments[2];var numFrames=30;var frame=0;var origValue=Number(obj.currentValue);if(obj.currentValue==null){obj.currentValue=obj.min;origValue=obj.min;}
var newValue=obj.value;var diff=newValue-origValue;var step=(diff/numFrames);var frame=0;function Grow()
{frame++;obj.value=((frame/numFrames)*diff)+origValue
if(obj.value>obj.max)obj.value=obj.max;if(obj.value<obj.min)obj.value=obj.min;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(frame<numFrames){RGraph.Effects.UpdateCanvas(Grow);}else if(typeof(callback)=='function'){callback(obj);}}
Grow();}
RGraph.Effects.Animate=function(obj,map)
{RGraph.RedrawCanvas(obj.canvas);RGraph.Effects.__total_frames__=(map&&map['frames'])?map['frames']:30;function Animate_Iterator(func)
{var id=[obj.id+'_'+obj.type];if(typeof(RGraph.Effects.__current_frame__)=='undefined'){RGraph.Effects.__current_frame__=new Array();RGraph.Effects.__original_values__=new Array();RGraph.Effects.__diffs__=new Array();RGraph.Effects.__steps__=new Array();RGraph.Effects.__callback__=new Array();}
if(!RGraph.Effects.__current_frame__[id]){RGraph.Effects.__current_frame__[id]=RGraph.Effects.__total_frames__;RGraph.Effects.__original_values__[id]={};RGraph.Effects.__diffs__[id]={};RGraph.Effects.__steps__[id]={};RGraph.Effects.__callback__[id]=func;}
for(var i in map){if(typeof(map[i])=='string'||typeof(map[i])=='number'){if(RGraph.Effects.__current_frame__[id]==RGraph.Effects.__total_frames__){RGraph.Effects.__original_values__[id][i]=obj.Get(i);RGraph.Effects.__diffs__[id][i]=map[i]-RGraph.Effects.__original_values__[id][i];RGraph.Effects.__steps__[id][i]=RGraph.Effects.__diffs__[id][i]/RGraph.Effects.__total_frames__;}
obj.Set(i,obj.Get(i)+RGraph.Effects.__steps__[id][i]);RGraph.RedrawCanvas(obj.canvas);}}
if(--RGraph.Effects.__current_frame__[id]>0){RGraph.Effects.UpdateCanvas(Animate_Iterator);}else{if(typeof(RGraph.Effects.__callback__[id])=='function'){(RGraph.Effects.__callback__[id])(obj);}
RGraph.Effects.__current_frame__[id]=null;RGraph.Effects.__original_values__[id]=null;RGraph.Effects.__diffs__[id]=null;RGraph.Effects.__steps__[id]=null;RGraph.Effects.__callback__[id]=null;}}
Animate_Iterator(arguments[2]);}
RGraph.Effects.jQuery.Slide.In=function(obj)
{RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);var canvas=obj.canvas;var div=RGraph.Effects.ReplaceCanvasWithDIV(obj.canvas);var delay=1000;div.style.overflow='hidden';var from=typeof(arguments[1])=='object'&&typeof(arguments[1]['from'])=='string'?arguments[1]['from']:'left';canvas.style.position='relative';if(from=='left'){canvas.style.left=(0-div.offsetWidth)+'px';canvas.style.top=0;}else if(from=='top'){canvas.style.left=0;canvas.style.top=(0-div.offsetHeight)+'px';}else if(from=='bottom'){canvas.style.left=0;canvas.style.top=div.offsetHeight+'px';}else{canvas.style.left=div.offsetWidth+'px';canvas.style.top=0;}
jQuery('#'+obj.id).animate({left:0,top:0},delay);if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.jQuery.Slide.Out=function(obj)
{var canvas=obj.canvas;var div=RGraph.Effects.ReplaceCanvasWithDIV(obj.canvas);var delay=1000;div.style.overflow='hidden';var to=typeof(arguments[1])=='object'&&arguments[1]&&typeof(arguments[1]['to'])=='string'?arguments[1]['to']:'left';canvas.style.position='relative';canvas.style.left=0;canvas.style.top=0;if(to=='left'){jQuery('#'+obj.id).animate({left:(0-canvas.width)+'px'},delay);}else if(to=='top'){jQuery('#'+obj.id).animate({left:0,top:(0-div.offsetHeight)+'px'},delay);}else if(to=='bottom'){jQuery('#'+obj.id).animate({top:(0+div.offsetHeight)+'px'},delay);}else{jQuery('#'+obj.id).animate({left:(0+canvas.width)+'px'},delay);}
if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.Line.Unfold=function(obj)
{obj.Set('chart.animation.factor',obj.Get('chart.animation.unfold.initial'));RGraph.Effects.Animate(obj,{'chart.animation.factor':1},arguments[2]);}
RGraph.Effects.Rose.RoundRobin=function(obj)
{var numFrames=60;var currentFrame=0;var original_margin=obj.Get('chart.margin');var margin=(360/obj.data.length)/2;var callback=arguments[2];obj.Set('chart.margin',margin);obj.Set('chart.animation.roundrobin.factor',0);function RoundRobin_inner()
{if(currentFrame++<numFrames){obj.Set('chart.animation.roundrobin.factor',currentFrame/numFrames);obj.Set('chart.margin',(currentFrame/numFrames)*original_margin);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);RGraph.Effects.UpdateCanvas(RoundRobin_inner);}else{obj.Set('chart.animation.roundrobin.factor',1);obj.Set('chart.margin',original_margin);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(typeof(callback)=='function'){callback(obj);}}}
RGraph.Effects.UpdateCanvas(RoundRobin_inner);}
RGraph.Effects.Line.UnfoldFromCenter=function(obj)
{var numFrames=30;var original_opacity=obj.canvas.style.opacity;obj.canvas.style.opacity=0;obj.Draw();RGraph.RedrawCanvas(obj.canvas);var center_value=obj.Get('chart.xaxispos')=='center'?obj.Get('chart.ymin'):((obj.max-obj.min)/2)+obj.min;obj.Set('chart.ymax',obj.scale2.max);RGraph.Clear(obj.canvas);obj.canvas.style.opacity=original_opacity;var original_data=RGraph.array_clone(obj.original_data);var callback=arguments[2];if(!obj.__increments__){obj.__increments__=new Array();for(var dataset=0;dataset<original_data.length;++dataset){obj.__increments__[dataset]=new Array();for(var i=0;i<original_data[dataset].length;++i){if(obj.Get('chart.filled')&&obj.Get('chart.filled.accumulative')&&dataset>0){obj.__increments__[dataset][i]=original_data[dataset][i]/numFrames;obj.original_data[dataset][i]=0;}else{obj.__increments__[dataset][i]=(original_data[dataset][i]-center_value)/numFrames;obj.original_data[dataset][i]=center_value;}}}}
function UnfoldFromCenter()
{RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);for(var dataset=0;dataset<original_data.length;++dataset){for(var i=0;i<original_data[dataset].length;++i){obj.original_data[dataset][i]+=obj.__increments__[dataset][i];}}
if(--numFrames>0){RGraph.Effects.UpdateCanvas(UnfoldFromCenter);}else{obj.original_data=RGraph.array_clone(original_data);obj.__increments__=null;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(typeof(callback)=='function'){callback(obj);}}}
UnfoldFromCenter();}
RGraph.Effects.Line.jQuery.UnfoldFromCenterTrace=function(obj)
{obj.canvas.style.visibility='hidden';setTimeout(function(){obj.canvas.style.visibility='visible';},10);obj.Draw();RGraph.Clear(obj.canvas);var data=RGraph.array_clone(obj.original_data);var callback=arguments[2];function Unfold_callback()
{obj.original_data=data;RGraph.Effects.Line.UnfoldFromCenter(obj,null,callback);}
var half=obj.Get('chart.xaxispos')=='center'?obj.min:((obj.max-obj.min)/2)+obj.min;obj.Set('chart.ymax',obj.max);for(var i=0;i<obj.original_data.length;++i){for(var j=0;j<obj.original_data[i].length;++j){obj.original_data[i][j]=(obj.Get('chart.filled')&&obj.Get('chart.filled.accumulative')&&i>0)?0:half;}}
RGraph.Effects.Line.jQuery.Trace(obj,{'duration':1000},Unfold_callback);}
RGraph.Effects.Line.FoldToCenter=function(obj)
{var totalFrames=30;var numFrame=totalFrames;RGraph.RedrawCanvas(obj.canvas);var center_value=obj.scale2.max/2;obj.Set('chart.ymax',obj.scale2.max);RGraph.Clear(obj.canvas);var original_data=RGraph.array_clone(obj.original_data);obj.Set('chart.shadow.blur',0);var callback=arguments[2];function FoldToCenter()
{for(var i=0;i<obj.data.length;++i){if(obj.data[i].length){for(var j=0;j<obj.data[i].length;++j){if(obj.original_data[i][j]>center_value){obj.original_data[i][j]=((original_data[i][j]-center_value)*(numFrame/totalFrames))+center_value;}else{obj.original_data[i][j]=center_value-((center_value-original_data[i][j])*(numFrame/totalFrames));}}}}
RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas)
if(numFrame-->0){RGraph.Effects.UpdateCanvas(FoldToCenter);}else if(typeof(callback)=='function'){callback(obj);}}
RGraph.Effects.UpdateCanvas(FoldToCenter);}
RGraph.Effects.Odo.Grow=function(obj)
{var numFrames=30;var curFrame=0;var origValue=Number(obj.currentValue);var newValue=obj.value;var diff=newValue-origValue;var step=(diff/numFrames);var callback=arguments[2];function Grow_inner()
{obj.value=origValue+(curFrame*step);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(++curFrame<=numFrames){RGraph.Effects.UpdateCanvas(Grow_inner);}else if(callback){callback(obj);}}
Grow_inner();}
RGraph.Effects.Meter.Grow=function(obj)
{if(!obj.currentValue){obj.currentValue=obj.min;}
var totalFrames=60;var numFrame=0;var diff=obj.value-obj.currentValue;var step=diff/totalFrames
var callback=arguments[2];var initial=obj.currentValue;function Grow_meter_inner()
{obj.value=initial+(numFrame++*step);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(numFrame++<=totalFrames){RGraph.Effects.UpdateCanvas(Grow_meter_inner);}else if(typeof(callback)=='function'){callback(obj);}}
Grow_meter_inner();}
RGraph.Effects.HBar.Grow=function(obj)
{obj.original_data=RGraph.array_clone(obj.data);obj.__animation_frame__=0;if(obj.Get('chart.xmax')==0){var xmax=0;for(var i=0;i<obj.data.length;++i){if(RGraph.is_array(obj.data[i])&&obj.Get('chart.grouping')=='stacked'){xmax=Math.max(xmax,RGraph.array_sum(obj.data[i]));}else if(RGraph.is_array(obj.data[i])&&obj.Get('chart.grouping')=='grouped'){xmax=Math.max(xmax,RGraph.array_max(obj.data[i]));}else{xmax=Math.max(xmax,RGraph.array_max(obj.data[i]));}}
var scale2=RGraph.getScale2(obj,{'max':xmax});obj.Set('chart.xmax',scale2.max);}
if(obj.Get('chart.shadow.blur')>0){var __original_shadow_blur__=obj.Get('chart.shadow.blur');obj.Set('chart.shadow.blur',0);}
function Grow()
{var numFrames=30;if(!obj.__animation_frame__){obj.__animation_frame__=0;obj.__original_vmargin__=obj.Get('chart.vmargin');obj.__vmargin__=((obj.canvas.height-obj.Get('chart.gutter.top')-obj.Get('chart.gutter.bottom'))/obj.data.length)/2;obj.Set('chart.vmargin',obj.__vmargin__);}
for(var j=0;j<obj.original_data.length;++j){var easing=Math.pow(Math.sin((obj.__animation_frame__*(90/numFrames))/(180/PI)),4);if(typeof(obj.data[j])=='object'){for(var k=0;k<obj.data[j].length;++k){obj.data[j][k]=(obj.__animation_frame__/numFrames)*obj.original_data[j][k]*easing;}}else{obj.data[j]=(obj.__animation_frame__/numFrames)*obj.original_data[j]*easing;}}
obj.Set('chart.vmargin',((1-(obj.__animation_frame__/numFrames))*(obj.__vmargin__-obj.__original_vmargin__))+obj.__original_vmargin__);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(obj.__animation_frame__<numFrames){obj.__animation_frame__+=1;RGraph.Effects.UpdateCanvas(Grow);}else{if(typeof(__original_shadow_blur__)=='number'&&__original_shadow_blur__>0){obj.Set('chart.shadow.blur',__original_shadow_blur__);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);}}}
RGraph.Effects.UpdateCanvas(Grow);}
RGraph.Effects.Line.jQuery.Trace=function(obj)
{var callback=typeof(arguments[2])=='function'?arguments[2]:function(){};var opt=arguments[1]||[];if(!opt['duration']){opt['duration']=1000;}
RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);var div=document.createElement('DIV');var xy=RGraph.getCanvasXY(obj.canvas);div.id='__rgraph_trace_animation_'+RGraph.random(0,4351623)+'__';div.style.left=xy[0]+'px';div.style.top=xy[1]+'px';div.style.width=obj.Get('chart.gutter.left');div.style.height=obj.canvas.height+'px';div.style.position='absolute';div.style.overflow='hidden';document.body.appendChild(div);obj.canvas.__rgraph_trace_div__=div;var id='__rgraph_line_trace_animation_'+RGraph.random(0,99999999)+'__';var canvas2=document.createElement('CANVAS');var properties=['WebkitTransform','MozTransform','OTransform','MSTransform','transform'];for(i in properties){var name=properties[i];if(typeof(obj.canvas.style[name])=='string'&&obj.canvas.style[name]){canvas2.style[name]=obj.canvas.style[name];}}
obj.canvas.__rgraph_line_canvas2__=canvas2;canvas2.width=obj.canvas.width;canvas2.height=obj.canvas.height;canvas2.style.position='absolute';canvas2.style.left=0;canvas2.style.top=0;canvas2.noclear=true;canvas2.id=id;div.appendChild(canvas2);var reposition_canvas2=function(e)
{var xy=RGraph.getCanvasXY(obj.canvas);div.style.left=xy[0]+'px';div.style.top=xy[1]+'px';}
window.addEventListener('resize',reposition_canvas2,false)
var obj2=new RGraph.Line(id,RGraph.array_clone(obj.original_data));RGraph.ObjectRegistry.Remove(obj2);for(i in obj.properties){if(typeof(i)=='string'){obj2.Set(i,obj.properties[i]);}}
obj2.Set('chart.labels',[]);obj2.Set('chart.background.grid',false);obj2.Set('chart.background.barcolor1','rgba(0,0,0,0)');obj2.Set('chart.background.barcolor2','rgba(0,0,0,0)');obj2.Set('chart.ylabels',false);obj2.Set('chart.noaxes',true);obj2.Set('chart.title','');obj2.Set('chart.title.xaxis','');obj2.Set('chart.title.yaxis','');obj2.Set('chart.filled.accumulative',obj.Get('chart.filled.accumulative'));obj.Set('chart.key',[]);obj2.Draw();obj.canvas.__rgraph_trace_obj2__=obj2;obj.Set('chart.line.visible',false);obj.Set('chart.colors',['rgba(0,0,0,0)']);if(obj.Get('chart.filled')){var original_fillstyle=obj.Get('chart.fillstyle');obj.Set('chart.fillstyle','rgba(0,0,0,0)');obj.Set('chart.animation.trace.original.fillstyle',original_fillstyle);}
RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(!obj.canvas.__rgraph_trace_cover__){var div2=document.createElement('DIV');div2.id='__rgraph_trace_animation_'+RGraph.random(0,4351623)+'__';div2.style.left=xy[0]+'px';div2.style.top=xy[1]+'px';div2.style.width=obj.canvas.width+'px';div2.style.height=obj.canvas.height+'px';div2.style.position='absolute';div2.style.overflow='hidden';div2.style.backgroundColor='rgba(0,0,0,0)';div.div2=div2;obj.canvas.__rgraph_trace_cover__=div2;document.body.appendChild(div2);}else{div2=obj.canvas.__rgraph_trace_cover__;}
jQuery('#'+div.id).animate({width:obj.canvas.width+'px'},opt['duration'],function(){RGraph.Effects.Line.Trace_callback(obj)});RGraph.Effects.Line.Trace_callback=function(obj)
{var obj2=obj.canvas.__rgraph_trace_obj2__;window.removeEventListener('resize',reposition_canvas2,false);div.style.display='none';div2.style.display='none';obj.Set('chart.line.visible',true);obj.Set('chart.filled',RGraph.array_clone(obj2.Get('chart.filled')));obj.Set('chart.fillstyle',obj.Get('chart.animation.trace.original.fillstyle'));obj.Set('chart.colors',RGraph.array_clone(obj2.Get('chart.colors')));obj.Set('chart.key',RGraph.array_clone(obj2.Get('chart.key')));RGraph.RedrawCanvas(obj.canvas);obj.canvas.__rgraph_trace_div__.style.display='none';obj.canvas.__rgraph_trace_div__=null;obj.canvas.__rgraph_line_canvas2__.style.display='none';obj.canvas.__rgraph_line_canvas2__=null;obj.canvas.__rgraph_trace_cover__.style.display='none';obj.canvas.__rgraph_trace_cover__=null;callback(obj);}}
RGraph.Effects.Line.Trace2=function(obj)
{var callback=arguments[2];var numFrames=(arguments[1]&&arguments[1].frames)?arguments[1].frames:60;var frame=0;obj.Set('animation.trace.clip',0);function Grow()
{if(frame>numFrames){if(callback){callback(obj);}
return;}
obj.Set('animation.trace.clip',frame/numFrames);RGraph.RedrawCanvas(obj.canvas);frame++;RGraph.Effects.UpdateCanvas(Grow);}
Grow();}
RGraph.Effects.Radar.Trace=function(obj)
{var callback=arguments[2];var numFrames=(arguments[1]&&arguments[1].frames)?arguments[1].frames:60;var frame=0;obj.Set('animation.trace.clip',0);function Grow()
{if(frame>numFrames){if(callback){callback(obj);}
return;}
obj.Set('animation.trace.clip',frame/numFrames);RGraph.RedrawCanvas(obj.canvas);frame++;RGraph.Effects.UpdateCanvas(Grow);}
Grow();}
RGraph.Effects.Pie.RoundRobin=function(obj)
{var callback=arguments[2]?arguments[2]:null;var opt=arguments[1];var currentFrame=0;var numFrames=(opt&&opt['frames'])?opt['frames']:90;var targetRadius=obj.getRadius();obj.Set('chart.events',false);if(obj.properties['chart.variant']=='donut'&&typeof(obj.properties['chart.variant.donut.width'])=='number'){if(RGraph.is_null(opt)){var opt={radius:null}}else{opt.radius=null;}}
function RoundRobin_inner()
{obj.Set('chart.effect.roundrobin.multiplier',Math.pow(Math.sin((currentFrame*(90/numFrames))/(180/PI)),2)*(currentFrame/numFrames));if(!opt||typeof(opt['radius'])=='undefined'||opt['radius']==true){obj.Set('chart.radius',targetRadius*obj.Get('chart.effect.roundrobin.multiplier'));}
RGraph.RedrawCanvas(obj.canvas);if(currentFrame++<numFrames){RGraph.Effects.UpdateCanvas(RoundRobin_inner);}else{obj.Set('chart.events',true);RGraph.RedrawCanvas(obj.canvas);if(callback){callback(obj);}}}
RGraph.Effects.UpdateCanvas(RoundRobin_inner);}
RGraph.Effects.Pie.Implode=function(obj)
{var numFrames=90;var distance=Math.min(obj.canvas.width,obj.canvas.height);var exploded=obj.Get('chart.exploded');var callback=arguments[2];function Implode_inner()
{obj.Set('chart.exploded',Math.sin(numFrames/(180/PI))*distance);RGraph.Clear(obj.canvas)
RGraph.RedrawCanvas(obj.canvas);if(numFrames>0){numFrames--;RGraph.Effects.UpdateCanvas(Implode_inner);}else{obj.Set('chart.exploded',exploded);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(typeof(callback)=='function'){callback(obj);}}}
RGraph.Effects.UpdateCanvas(Implode_inner);}
RGraph.Effects.Pie.Explode=function(obj)
{var canvas=obj.canvas;var opts=arguments[1]?arguments[1]:[];var callback=arguments[2]?arguments[2]:null;var frames=opts['frames']?opts['frames']:60;obj.Set('chart.exploded',0);RGraph.Effects.Animate(obj,{'frames':frames,'chart.exploded':Math.max(canvas.width,canvas.height)},callback);}
RGraph.Effects.Gauge.Grow=function(obj)
{var callback=arguments[2];var numFrames=30;var frame=0;if(typeof(obj.value)=='number'){var origValue=Number(obj.currentValue);if(obj.currentValue==null){obj.currentValue=obj.min;origValue=obj.min;}
var newValue=obj.value;var diff=newValue-origValue;var step=(diff/numFrames);var frame=0;function Grow_single()
{frame++;obj.value=((frame/numFrames)*diff)+origValue;if(obj.value>obj.max)obj.value=obj.max;if(obj.value<obj.min)obj.value=obj.min;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(frame<30){RGraph.Effects.UpdateCanvas(Grow_single);}else if(typeof(callback)=='function'){callback(obj);}}
Grow_single();}else{if(obj.currentValue==null){obj.currentValue=[];for(var i=0;i<obj.value.length;++i){obj.currentValue[i]=obj.min;}
origValue=RGraph.array_clone(obj.currentValue);}
var origValue=RGraph.array_clone(obj.currentValue);var newValue=RGraph.array_clone(obj.value);var diff=[];var step=[];for(var i=0;i<newValue.length;++i){diff[i]=newValue[i]-Number(obj.currentValue[i]);step[i]=(diff[i]/numFrames);}
function Grow_multiple()
{frame++;for(var i=0;i<obj.value.length;++i){obj.value[i]=((frame/numFrames)*diff[i])+origValue[i];if(obj.value[i]>obj.max)obj.value[i]=obj.max;if(obj.value[i]<obj.min)obj.value[i]=obj.min;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);}
if(frame<30){RGraph.Effects.UpdateCanvas(Grow_multiple);}else if(typeof(callback)=='function'){callback(obj);}}
Grow_multiple();}}
RGraph.Effects.Radar.Grow=function(obj)
{var totalframes=30;var framenum=totalframes;var data=RGraph.array_clone(obj.data);var callback=arguments[2];obj.original_data=RGraph.array_clone(obj.original_data);function Grow_inner()
{for(var i=0;i<data.length;++i){if(obj.original_data[i]==null){obj.original_data[i]=[];}
for(var j=0;j<data[i].length;++j){obj.original_data[i][j]=((totalframes-framenum)/totalframes)*data[i][j];}}
RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(framenum>0){framenum--;RGraph.Effects.UpdateCanvas(Grow_inner);}else if(typeof(callback)=='function'){callback(obj);}}
RGraph.Effects.UpdateCanvas(Grow_inner);}
RGraph.Effects.Waterfall.Grow=function(obj)
{var totalFrames=45;var numFrame=0;var data=RGraph.array_clone(obj.data);var callback=arguments[2];for(var i=0;i<obj.data.length;++i){obj.data[i]/=totalFrames;}
if(obj.Get('chart.ymax')==null){var max=obj.getMax(data);var scale2=RGraph.getScale2(obj,{'max':max});obj.Set('chart.ymax',scale2.max);}
function Grow_inner()
{for(var i=0;i<obj.data.length;++i){obj.data[i]=data[i]*(numFrame/totalFrames);}
var multiplier=Math.pow(Math.sin(((numFrame/totalFrames)*90)/(180/PI)),20);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(numFrame++<totalFrames){RGraph.Effects.UpdateCanvas(Grow_inner);}else if(typeof(callback)=='function'){callback(obj);}}
RGraph.Effects.UpdateCanvas(Grow_inner)}
RGraph.Effects.Bar.Wave2=RGraph.Effects.Bar.Wave=function(obj)
{var totalframes=(arguments[1]&&arguments[1].frames)?arguments[1].frames:15;var original_data=[];obj.Draw();obj.Set('chart.ymax',obj.scale2.max);RGraph.Clear(obj.canvas);for(var i=0;i<obj.data.length;++i){(function(idx)
{original_data[i]=obj.data[i];obj.data[i]=typeof(obj.data[i])=='object'?[]:0;setTimeout(function(){Iterator(idx,totalframes);},100*i)})(i);}
function Iterator(idx,frames)
{if(frames-->0){if(typeof(obj.data[idx])=='number'){obj.data[idx]=((totalframes-frames)/totalframes)*original_data[idx]}else if(typeof(obj.data[idx])=='object'){for(var k=0;k<original_data[idx].length;++k){obj.data[idx][k]=((totalframes-frames)/totalframes)*original_data[idx][k];}}
RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);RGraph.Effects.UpdateCanvas(function(){Iterator(idx,frames);});}}}
RGraph.Effects.VProgress.Grow=RGraph.Effects.HProgress.Grow=function(obj)
{var canvas=obj.canvas;var context=obj.context;var initial_value=obj.currentValue;var numFrames=30;var currentFrame=0
if(typeof(obj.value)=='object'){if(RGraph.is_null(obj.currentValue)){obj.currentValue=[];for(var i=0;i<obj.value.length;++i){obj.currentValue[i]=0;}}
var diff=[];var increment=[];for(var i=0;i<obj.value.length;++i){diff[i]=obj.value[i]-Number(obj.currentValue[i]);increment[i]=diff[i]/numFrames;}
if(initial_value==null){initial_value=[];for(var i=0;i<obj.value.length;++i){initial_value[i]=0;}}}else{var diff=obj.value-Number(obj.currentValue);var increment=diff/numFrames;}
var callback=arguments[2]?arguments[2]:null;function Grow()
{currentFrame++;if(currentFrame<=numFrames){if(typeof(obj.value)=='object'){obj.value=[];for(var i=0;i<initial_value.length;++i){obj.value[i]=initial_value[i]+(increment[i]*currentFrame);}}else{obj.value=initial_value+(increment*currentFrame);}
RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);RGraph.Effects.UpdateCanvas(Grow);}else if(callback){callback(obj);}}
RGraph.Effects.UpdateCanvas(Grow);}
RGraph.Effects.Gantt.Grow=function(obj)
{var canvas=obj.canvas;var context=obj.context;var numFrames=30;var currentFrame=0;var callback=arguments[2]?arguments[2]:null;var events=obj.data;var original_events=RGraph.array_clone(events);function Grow_gantt_inner()
{if(currentFrame<=numFrames){for(var i=0;i<events.length;++i){if(typeof(events[i][0])=='object'){for(var j=0;j<events[i].length;++j){events[i][j][1]=(currentFrame/numFrames)*original_events[i][j][1];}}else{events[i][1]=(currentFrame/numFrames)*original_events[i][1];}}
obj.data=events;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);currentFrame++;RGraph.Effects.UpdateCanvas(Grow_gantt_inner);}else if(callback){callback(obj);}}
RGraph.Effects.UpdateCanvas(Grow_gantt_inner);}
if(!Function.prototype.bind){Function.prototype.bind=function(oThis){if(typeof this!=="function"){if(console&&console.log){console.log('Function.prototype.bind - what is trying to be bound is not callable');}}
var aArgs=Array.prototype.slice.call(arguments,1),fToBind=this,fNOP=function(){},fBound=function(){return fToBind.apply(this instanceof fNOP?this:oThis||window,aArgs.concat(Array.prototype.slice.call(arguments)));};fNOP.prototype=this.prototype;fBound.prototype=new fNOP();return fBound;};}
RGraph.Effects.Rose.Explode=function(obj)
{var canvas=obj.canvas;var opts=arguments[1]?arguments[1]:[];var callback=arguments[2]?arguments[2]:null;var frames=opts['frames']?opts['frames']:60;obj.Set('chart.exploded',0);RGraph.Effects.Animate(obj,{'frames':frames,'chart.exploded':Math.min(canvas.width,canvas.height)},callback);}
RGraph.Effects.Rose.Implode=function(obj)
{var canvas=obj.canvas;var opts=arguments[1]?arguments[1]:[];var callback=arguments[2]?arguments[2]:null;var frames=opts['frames']?opts['frames']:60;obj.Set('chart.exploded',Math.min(canvas.width,canvas.height));RGraph.Effects.Animate(obj,{'frames':frames,'chart.exploded':0},callback);}
RGraph.Effects.Thermometer.Grow=function(obj)
{var callback=arguments[2];var numFrames=30;var origValue=Number(obj.currentValue);if(obj.currentValue==null){obj.currentValue=0
origValue=0;}
var newValue=obj.value;var diff=newValue-origValue;var step=(diff/numFrames);var frame=0;function Grow()
{frame++
obj.value=v=((frame/numFrames)*diff)+origValue
RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(frame<30){RGraph.Effects.UpdateCanvas(Grow);}else if(typeof(callback)=='function'){callback(obj);}}
RGraph.Effects.UpdateCanvas(Grow);}
RGraph.Effects.Scatter.jQuery.Trace=function(obj)
{var callback=typeof(arguments[2])=='function'?arguments[2]:function(){};var opt=arguments[1]||[];if(!opt['duration']){opt['duration']=1500;}
RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);var div=document.createElement('DIV');var xy=RGraph.getCanvasXY(obj.canvas);div.id='__rgraph_trace_animation_'+RGraph.random(0,4351623)+'__';div.style.left=xy[0]+'px';div.style.top=xy[1]+'px';div.style.width=obj.Get('chart.gutter.left');div.style.height=obj.canvas.height+'px';div.style.position='absolute';div.style.overflow='hidden';document.body.appendChild(div);var id='__rgraph_scatter_trace_animation_'+RGraph.random(0,99999999)+'__';var canvas2=document.createElement('CANVAS');canvas2.width=obj.canvas.width;canvas2.height=obj.canvas.height;canvas2.style.position='absolute';canvas2.style.left=0;canvas2.style.top=0;canvas2.noclear=true;canvas2.id=id;div.appendChild(canvas2);var reposition_canvas2=function(e)
{var xy=RGraph.getCanvasXY(obj.canvas);div.style.left=xy[0]+'px';div.style.top=xy[1]+'px';}
window.addEventListener('resize',reposition_canvas2,false)
var obj2=new RGraph.Scatter(id,RGraph.array_clone(obj.data));RGraph.ObjectRegistry.Remove(obj2);for(i in obj.properties){if(typeof(i)=='string'){obj2.Set(i,obj.properties[i]);}}
obj2.Set('chart.labels',[]);obj2.Set('chart.background.grid',false);obj2.Set('chart.background.barcolor1','rgba(0,0,0,0)');obj2.Set('chart.background.barcolor2','rgba(0,0,0,0)');obj2.Set('chart.ylabels',false);obj2.Set('chart.noaxes',true);obj2.Set('chart.title','');obj2.Set('chart.title.xaxis','');obj2.Set('chart.title.yaxis','');obj.Set('chart.key',[]);obj2.Draw();obj.Set('chart.line.visible',false);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(!obj.canvas.__rgraph_scatter_trace_cover__){var div2=document.createElement('DIV');div2.id='__rgraph_trace_animation_'+RGraph.random(0,4351623)+'__';div2.style.left=xy[0]+'px';div2.style.top=xy[1]+'px';div2.style.width=obj.canvas.width+'px';div2.style.height=obj.canvas.height+'px';div2.style.position='absolute';div2.style.overflow='hidden';div2.style.backgroundColor='rgba(0,0,0,0)';div.div2=div2;obj.canvas.__rgraph_scatter_trace_cover__=div2
document.body.appendChild(div2);}else{div2=obj.canvas.__rgraph_scatter_trace_cover__;}
jQuery('#'+div.id).animate({width:obj.canvas.width+'px'},opt['duration'],function(){window.removeEventListener('resize',reposition_canvas2,false);div.style.display='none';div2.style.display='none';obj.Set('chart.line.visible',true);obj.Set('chart.colors',RGraph.array_clone(obj2.Get('chart.colors')));obj.Set('chart.key',RGraph.array_clone(obj2.Get('chart.key')));RGraph.RedrawCanvas(obj.canvas);obj.canvas.__rgraph_trace_cover__=null;callback(obj);});}
RGraph.Effects.CornerGauge.Grow=function(obj)
{var callback=arguments[2];var numFrames=30;var frame=0;if(typeof(obj.value)=='number'){var origValue=Number(obj.currentValue);if(obj.currentValue==null){obj.currentValue=obj.min;origValue=obj.min;}
var newValue=obj.value;var diff=newValue-origValue;var step=(diff/numFrames);var frame=0;function Grow_single()
{frame++;obj.value=((frame/numFrames)*diff)+origValue
if(obj.value>obj.max)obj.value=obj.max;if(obj.value<obj.min)obj.value=obj.min;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(frame<30){RGraph.Effects.UpdateCanvas(Grow_single);}else if(typeof(callback)=='function'){callback(obj);}}
Grow_single();}else{if(obj.currentValue==null){obj.currentValue=[];for(var i=0;i<obj.value.length;++i){obj.currentValue[i]=obj.min;}
origValue=RGraph.array_clone(obj.currentValue);}
var origValue=RGraph.array_clone(obj.currentValue);var newValue=RGraph.array_clone(obj.value);var diff=[];var step=[];for(var i=0;i<newValue.length;++i){diff[i]=newValue[i]-Number(obj.currentValue[i]);step[i]=(diff[i]/numFrames);}
function Grow_multiple()
{frame++;for(var i=0;i<obj.value.length;++i){obj.value[i]=((frame/numFrames)*diff[i])+origValue[i];if(obj.value[i]>obj.max)obj.value[i]=obj.max;if(obj.value[i]<obj.min)obj.value[i]=obj.min;RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);}
if(frame<30){RGraph.Effects.UpdateCanvas(Grow_multiple);}else if(typeof(callback)=='function'){callback(obj);}}
Grow_multiple();}}
RGraph.Effects.Rose.Grow=function(obj)
{var callback=arguments[2];var numFrames=60;var frame=0;function Grow()
{frame++;obj.Set('chart.animation.grow.multiplier',frame/numFrames);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(frame<numFrames){++frame;RGraph.Effects.UpdateCanvas(Grow);}else{obj.Set('chart.animation.grow.multiplier',1);RGraph.Clear(obj.canvas);RGraph.RedrawCanvas(obj.canvas);if(typeof(callback)=='function'){callback(obj);}}}
RGraph.Effects.UpdateCanvas(Grow);}
RGraph.Effects.jQuery.HScissors.Open=function(obj)
{var canvas=obj.isRGraph?obj.canvas:obj;;var id=canvas.id;var opts=arguments[1]?arguments[1]:[];var delay=1000;var color=opts['color']?opts['color']:'white';var xy=RGraph.getCanvasXY(canvas);var height=canvas.height/5;RGraph.Clear(canvas);RGraph.RedrawCanvas(canvas);for(var i=0;i<5;++i){var div=document.getElementById(id+"scissors_"+i)
if(!div){var div=document.createElement('DIV');div.id=id+'scissors_'+i;div.style.width=canvas.width+'px';div.style.height=height+'px';div.style.left=xy[0]+'px';div.style.top=(xy[1]+(canvas.height*(i/5)))+'px';div.style.position='absolute';div.style.backgroundColor=color;document.body.appendChild(div);}
if(i%2==0){jQuery('#'+id+'scissors_'+i).animate({left:canvas.width+'px',width:0},delay);}else{jQuery('#'+id+'scissors_'+i).animate({width:0},delay);}}
setTimeout(function(){document.body.removeChild(document.getElementById(id+'scissors_0'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById(id+'scissors_1'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById(id+'scissors_2'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById(id+'scissors_3'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById(id+'scissors_4'));},delay);if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.jQuery.HScissors.Close=function(obj)
{var canvas=obj.isRGraph?obj.canvas:obj;var id=canvas.id;var opts=arguments[1]?arguments[1]:[];var delay=1000;var color=opts['color']?opts['color']:'white';var xy=RGraph.getCanvasXY(canvas);var height=canvas.height/5;RGraph.Clear(canvas);RGraph.RedrawCanvas(canvas);for(var i=0;i<5;++i){var div=document.createElement('DIV');div.id=id+'_scissors_'+i;div.style.width=0;div.style.height=height+'px';div.style.left=(i%2==0?xy[0]+canvas.width:xy[0])+'px';div.style.top=(xy[1]+(canvas.height*(i/5)))+'px';div.style.position='absolute';div.style.backgroundColor=color;document.body.appendChild(div);if(i%2==0){jQuery('#'+id+'_scissors_'+i).animate({left:xy[0]+'px',width:canvas.width+'px'},delay);}else{jQuery('#'+id+'_scissors_'+i).animate({width:canvas.width+'px'},delay);}}
if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.jQuery.VScissors.Open=function(obj)
{var canvas=obj.isRGraph?obj.canvas:obj;;var id=canvas.id;var opts=arguments[1]?arguments[1]:[];var delay=1000;var color=opts['color']?opts['color']:'white';var xy=RGraph.getCanvasXY(canvas);var width=canvas.width/5;RGraph.Clear(canvas);RGraph.RedrawCanvas(canvas);for(var i=0;i<5;++i){var div=document.getElementById(id+"_vscissors_"+i)
if(!div){var div=document.createElement('DIV');div.id=id+'_vscissors_'+i;div.style.width=width+'px';div.style.height=canvas.height+'px';div.style.left=xy[0]+(canvas.width*(i/5))+'px';div.style.top=xy[1]+'px';div.style.position='absolute';div.style.backgroundColor=color;document.body.appendChild(div);}
if(i%2==0){jQuery('#'+id+'_vscissors_'+i).animate({top:xy[1]+canvas.height+'px',height:0},delay);}else{jQuery('#'+id+'_vscissors_'+i).animate({height:0},delay);}}
setTimeout(function(){document.body.removeChild(document.getElementById(id+'_vscissors_0'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById(id+'_vscissors_1'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById(id+'_vscissors_2'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById(id+'_vscissors_3'));},delay);setTimeout(function(){document.body.removeChild(document.getElementById(id+'_vscissors_4'));},delay);if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}
RGraph.Effects.jQuery.VScissors.Close=function(obj)
{var canvas=obj.isRGraph?obj.canvas:obj;var id=canvas.id;var opts=arguments[1]?arguments[1]:[];var delay=1000;var color=opts['color']?opts['color']:'white';var xy=RGraph.getCanvasXY(canvas);var width=canvas.width/5;RGraph.Clear(canvas);RGraph.RedrawCanvas(canvas);for(var i=0;i<5;++i){var div=document.getElementById(id+"_vscissors_"+i)
if(!div){var div=document.createElement('DIV');div.id=id+'_vscissors_'+i;div.style.width=width+'px';div.style.height=0;div.style.left=xy[0]+(width*i)+'px';div.style.top=(i%2==0?xy[1]+canvas.height:xy[1])+'px';div.style.position='absolute';div.style.backgroundColor=color;document.body.appendChild(div);}
if(i%2==0){jQuery('#'+id+'_vscissors_'+i).animate({top:xy[1]+'px',height:canvas.height+'px'},delay);}else{jQuery('#'+id+'_vscissors_'+i).animate({height:canvas.height+'px'},delay);}}
if(typeof(arguments[2])=='function'){setTimeout(arguments[2],delay);}}


if(typeof(RGraph)=='undefined')RGraph={isRGraph:true,type:'common'};RGraph.InstallWindowMousedownListener=function(obj)
{if(!window.__rgraph_mousedown_event_listener_installed__){var func=function(e)
{if(navigator.userAgent.indexOf('Firefox')>=0)window.event=e;e=RGraph.FixEventObject(e);if(typeof(window.onmousedown_rgraph)=='function'){window.onmousedown_rgraph(e);}
if(RGraph.HideTooltip&&RGraph.Registry.Get('chart.tooltip')){RGraph.Clear(RGraph.Registry.Get('chart.tooltip').__canvas__);RGraph.Redraw();RGraph.HideTooltip();}}
window.addEventListener('mousedown',func,false);window.__rgraph_mousedown_event_listener_installed__=func;}}
RGraph.InstallWindowMouseupListener=function(obj)
{if(!window.__rgraph_mouseup_event_listener_installed__){var func=function(e)
{if(navigator.userAgent.indexOf('Firefox')>=0)window.event=e;e=RGraph.FixEventObject(e);if(RGraph.Annotating_window_onmouseup){RGraph.Annotating_window_onmouseup(e);return;}
if(typeof(window.onmouseup_rgraph)=='function'){window.onmouseup_rgraph(e);}
if(RGraph.Registry.Get('chart.adjusting')||RGraph.Registry.Get('chart.adjusting.gantt')){RGraph.FireCustomEvent(RGraph.Registry.Get('chart.adjusting'),'onadjustend');}
RGraph.Registry.Set('chart.adjusting',null);RGraph.Registry.Set('chart.adjusting.shape',null);RGraph.Registry.Set('chart.adjusting.gantt',null);var tags=document.getElementsByTagName('canvas');for(var i=0;i<tags.length;++i){if(tags[i].__object__&&tags[i].__object__.isRGraph){if(!tags[i].__object__.Get('chart.annotatable')){if(!tags[i].__rgraph_trace_cover__&&!noredraw){RGraph.Clear(tags[i]);}else{var noredraw=true;}}}}
if(!noredraw){RGraph.Redraw();}}
window.addEventListener('mouseup',func,false);window.__rgraph_mouseup_event_listener_installed__=func;}}
RGraph.InstallCanvasMouseupListener=function(obj)
{if(!obj.canvas.__rgraph_mouseup_event_listener_installed__){var func=function(e)
{if(navigator.userAgent.indexOf('Firefox')>=0)window.event=e;e=RGraph.FixEventObject(e);if(typeof(e.target.onmouseup_rgraph)=='function'){e.target.onmouseup_rgraph(e);}
var objects=RGraph.ObjectRegistry.getObjectsByXY(e);if(objects){for(var i=0;i<objects.length;++i){var obj=objects[i];var id=objects[i].id;if(!RGraph.is_null(obj)&&RGraph.Tooltip){var shape=obj.getShape(e);if(shape&&shape['tooltip']){var text=shape['tooltip'];if(text){var type=shape['object'].type;if(type=='line'||type=='rscatter'||(type=='scatter'&&!obj.Get('chart.boxplot'))||type=='radar'){var canvasXY=RGraph.getCanvasXY(obj.canvas);var x=canvasXY[0]+shape['x'];var y=canvasXY[1]+shape['y'];}else{var x=e.pageX;var y=e.pageY;}
RGraph.Clear(obj.canvas);RGraph.Redraw();obj.Highlight(shape);RGraph.Registry.Set('chart.tooltip.shape',shape);RGraph.Tooltip(obj,text,x,y,shape['index'],e);if(RGraph.Registry.Get('chart.tooltip')){RGraph.Registry.Get('chart.tooltip').__shape__=shape;RGraph.EvaluateCursor(e);}
e.cancelBubble=true;e.stopPropagation();return false;}}}
if(RGraph.Registry.Get('chart.adjusting')||RGraph.Registry.Get('chart.adjusting.gantt')){RGraph.FireCustomEvent(RGraph.Registry.Get('chart.adjusting'),'onadjustend');}
RGraph.Registry.Set('chart.adjusting',null);RGraph.Registry.Set('chart.adjusting.shape',null);RGraph.Registry.Set('chart.adjusting.gantt',null);if(shape||(obj.overChartArea&&obj.overChartArea(e))){break;}}}}
obj.canvas.addEventListener('mouseup',func,false);obj.canvas.__rgraph_mouseup_event_listener_installed__=func;}}
RGraph.InstallCanvasMousemoveListener=function(obj)
{if(!obj.canvas.__rgraph_mousemove_event_listener_installed__){var func=function(e)
{if(navigator.userAgent.indexOf('Firefox')>=0)window.event=e;e=RGraph.FixEventObject(e);if(typeof(e.target.onmousemove_rgraph)=='function'){e.target.onmousemove_rgraph(e);}
var objects=RGraph.ObjectRegistry.getObjectsByXY(e);if(objects&&objects.length){for(var i=0;i<objects.length;++i){var obj=objects[i];var id=obj.id;if(!obj.getShape){continue;}
var shape=obj.getShape(e);var func=obj.Get('chart.events.mousemove');if(!func&&typeof(obj.onmousemove)=='function'){var func=obj.onmousemove;}
if(shape){var index=shape['object'].type=='scatter'?shape['index_adjusted']:shape['index'];if(typeof(obj['$'+index])=='object'&&typeof(obj['$'+index].onmousemove)=='function'){var func2=obj['$'+index].onmousemove;}}
if(shape&&(typeof(func)=='function'||typeof(func2)=='function')){if(obj.Get('chart.events.mousemove.revertto')==null){obj.Set('chart.events.mousemove.revertto',e.target.style.cursor);}
if(typeof(func)=='function')func(e,shape);if(typeof(func2)=='function')func2(e,shape);}else if(typeof(obj.Get('chart.events.mousemove.revertto'))=='string'){RGraph.cursor.push('default');obj.Set('chart.events.mousemove.revertto',null);}
if(shape&&(obj.Get('chart.tooltips')&&obj.Get('chart.tooltips')[shape['index']]||shape['tooltip'])&&(obj.Get('chart.tooltips.event')=='onmousemove'||obj.Get('chart.tooltips.event')=='mousemove')&&(RGraph.is_null(RGraph.Registry.Get('chart.tooltip'))||RGraph.Registry.Get('chart.tooltip').__index__!=shape['index']||(typeof(shape['dataset'])=='number'&&shape['dataset']!=RGraph.Registry.Get('chart.tooltip').__shape__['dataset'])||obj.uid!=RGraph.Registry.Get('chart.tooltip').__object__.uid)){RGraph.Clear(obj.canvas);RGraph.Redraw();obj.canvas.__rgraph_mouseup_event_listener_installed__(e);return;}
if(obj&&obj.Get('chart.adjustable')){obj.Adjusting_mousemove(e);}
if(shape||(obj.overChartArea&&obj.overChartArea(e))){break;}}}
if(e.target&&e.target.__object__&&e.target.__object__.Get('chart.crosshairs')){RGraph.DrawCrosshairs(e,e.target.__object__);}
if(typeof(InteractiveKey_line_mousemove)=='function')InteractiveKey_line_mousemove(e);if(typeof(InteractiveKey_pie_mousemove)=='function')InteractiveKey_pie_mousemove(e);if(e.target.__object__&&e.target.__object__.Get('chart.annotatable')&&RGraph.Annotating_canvas_onmousemove){RGraph.Annotating_canvas_onmousemove(e);}
RGraph.EvaluateCursor(e);}
obj.canvas.addEventListener('mousemove',func,false);obj.canvas.__rgraph_mousemove_event_listener_installed__=func;}}
RGraph.InstallCanvasMousedownListener=function(obj)
{if(!obj.canvas.__rgraph_mousedown_event_listener_installed__){var func=function(e)
{if(navigator.userAgent.indexOf('Firefox')>=0)window.event=e;e=RGraph.FixEventObject(e);if(typeof(e.target.onmousedown_rgraph)=='function'){e.target.onmousedown_rgraph(e);}
if(e.target.__object__&&e.target.__object__.Get('chart.annotatable')&&RGraph.Annotating_canvas_onmousedown){RGraph.Annotating_canvas_onmousedown(e);return;}
var obj=RGraph.ObjectRegistry.getObjectByXY(e);if(obj){var id=obj.id;if(obj&&obj.isRGraph&&obj.Get('chart.adjustable')){var obj=RGraph.ObjectRegistry.getObjectByXY(e);if(obj&&obj.isRGraph){switch(obj.type){case'bar':var shape=obj.getShapeByX(e);break;case'gantt':var shape=obj.getShape(e);if(shape){var mouseXY=RGraph.getMouseXY(e);RGraph.Registry.Set('chart.adjusting.gantt',{'index':shape['index'],'object':obj,'mousex':mouseXY[0],'mousey':mouseXY[1],'event_start':obj.data[shape['index']][0],'event_duration':obj.data[shape['index']][1],'mode':(mouseXY[0]>(shape['x']+shape['width']-5)?'resize':'move'),'shape':shape});}
break;case'line':var shape=obj.getShape(e);break;default:var shape=null;}
RGraph.Registry.Set('chart.adjusting.shape',shape);RGraph.FireCustomEvent(obj,'onadjustbegin');RGraph.Registry.Set('chart.adjusting',obj);RGraph.Clear(obj.canvas);RGraph.Redraw();obj.canvas.__rgraph_mousemove_event_listener_installed__(e);}}
RGraph.Clear(obj.canvas);RGraph.Redraw();}}
obj.canvas.addEventListener('mousedown',func,false);obj.canvas.__rgraph_mousedown_event_listener_installed__=func;}}
RGraph.InstallCanvasClickListener=function(obj)
{if(!obj.canvas.__rgraph_click_event_listener_installed__){var func=function(e)
{if(navigator.userAgent.indexOf('Firefox')>=0)window.event=e;e=RGraph.FixEventObject(e);if(typeof(e.target.onclick_rgraph)=='function'){e.target.onclick_rgraph(e);}
var objects=RGraph.ObjectRegistry.getObjectsByXY(e);for(var i=0;i<objects.length;++i){var obj=objects[i];var id=obj.id;var shape=obj.getShape(e);var func=obj.Get('chart.events.click');if(!func&&typeof(obj.onclick)=='function'){func=obj.onclick;}
if(shape&&typeof(func)=='function'){func(e,shape);return;}
if(shape){var index=shape['object'].type=='scatter'?shape['index_adjusted']:shape['index'];if(typeof(index)=='number'&&obj['$'+index]){var func=obj['$'+index].onclick;if(typeof(func)=='function'){func(e,shape);return;}}}
if(shape||(obj.overChartArea&&obj.overChartArea(e))){break;}}}
obj.canvas.addEventListener('click',func,false);obj.canvas.__rgraph_click_event_listener_installed__=func;}}
RGraph.EvaluateCursor=function(e)
{var mouseXY=RGraph.getMouseXY(e);var mouseX=mouseXY[0];var mouseY=mouseXY[1];var canvas=e.target;var objects=RGraph.ObjectRegistry.getObjectsByCanvasID(canvas.id);for(var i=0;i<objects.length;++i){if((objects[i].getShape&&objects[i].getShape(e))||(objects[i].overChartArea&&objects[i].overChartArea(e))){var obj=objects[i];var id=obj.id;}}
if(!RGraph.is_null(obj)){if(obj.getShape&&obj.getShape(e)){var shape=obj.getShape(e);if(obj.Get('chart.tooltips')){var text=RGraph.parseTooltipText(obj.Get('chart.tooltips'),shape['index']);if(!text&&shape['object'].type=='scatter'&&shape['index_adjusted']){text=RGraph.parseTooltipText(obj.Get('chart.tooltips'),shape['index_adjusted']);}
if(text){var pointer=true;}}}
if(!RGraph.is_null(obj)&&obj.Get('chart.key.interactive')){for(var j=0;j<obj.coords.key.length;++j){if(mouseX>obj.coords.key[j][0]&&mouseX<(obj.coords.key[j][0]+obj.coords.key[j][2])&&mouseY>obj.coords.key[j][1]&&mouseY<(obj.coords.key[j][1]+obj.coords.key[j][3])){var pointer=true;}}}}
if(!RGraph.is_null(shape)&&!RGraph.is_null(obj)){if(!RGraph.is_null(obj.Get('chart.events.mousemove'))&&typeof(obj.Get('chart.events.mousemove'))=='function'){var str=(obj.Get('chart.events.mousemove')).toString();if(str.match(/pointer/)&&str.match(/cursor/)&&str.match(/style/)){var pointer=true;}}
if(!RGraph.is_null(obj.onmousemove)&&typeof(obj.onmousemove)=='function'){var str=(obj.onmousemove).toString();if(str.match(/pointer/)&&str.match(/cursor/)&&str.match(/style/)){var pointer=true;}}
var index=shape['object'].type=='scatter'?shape['index_adjusted']:shape['index'];if(!RGraph.is_null(obj['$'+index])&&typeof(obj['$'+index].onmousemove)=='function'){var str=(obj['$'+index].onmousemove).toString();if(str.match(/pointer/)&&str.match(/cursor/)&&str.match(/style/)){var pointer=true;}}}
var objects=RGraph.ObjectRegistry.objects.byCanvasID;for(var i=0;i<objects.length;++i){if(objects[i]&&objects[i][1].Get('chart.resizable')){var resizable=true;}}
if(resizable&&mouseX>(e.target.width-32)&&mouseY>(e.target.height-16)){pointer=true;}
if(pointer){e.target.style.cursor='pointer';}else if(e.target.style.cursor=='pointer'){e.target.style.cursor='default';}else{e.target.style.cursor=null;}
if(resizable&&mouseX>=(e.target.width-15)&&mouseY>=(e.target.height-15)){e.target.style.cursor='move';}
if(typeof(mouse_over_key)=='boolean'&&mouse_over_key){e.target.style.cursor='pointer';}
if(obj&&obj.type=='gantt'&&obj.Get('chart.adjustable')){if(obj.getShape&&obj.getShape(e)){e.target.style.cursor='ew-resize';}else{e.target.style.cursor='default';}}
if(obj&&obj.type=='line'&&obj.Get('chart.adjustable')){if(obj.getShape&&obj.getShape(e)){e.target.style.cursor='ns-resize';}else{e.target.style.cursor='default';}}
if(e.target.__object__&&e.target.__object__.Get('chart.annotatable')){e.target.style.cursor='crosshair';}}
RGraph.parseTooltipText=function(tooltips,idx)
{if(!tooltips){return null;}
if(typeof(tooltips)=='function'){var text=tooltips(idx);}else if(typeof(tooltips)=='string'){var text=tooltips;}else if(typeof(tooltips)=='object'&&typeof(tooltips)[idx]=='function'){var text=tooltips[idx](idx);}else if(typeof(tooltips)[idx]=='string'&&tooltips[idx]){var text=tooltips[idx];}else{var text='';}
if(text=='undefined'){text='';}else if(text=='null'){text='';}
return RGraph.getTooltipTextFromDIV?RGraph.getTooltipTextFromDIV(text):text;}
RGraph.DrawCrosshairs=function(e,obj)
{var e=RGraph.FixEventObject(e);var width=obj.canvas.width;var height=obj.canvas.height;var mouseXY=RGraph.getMouseXY(e);var x=mouseXY[0];var y=mouseXY[1];var gutterLeft=obj.gutterLeft;var gutterRight=obj.gutterRight;var gutterTop=obj.gutterTop;var gutterBottom=obj.gutterBottom;var prop=obj.properties;RGraph.RedrawCanvas(obj.canvas);if(x>=gutterLeft&&y>=gutterTop&&x<=(width-gutterRight)&&y<=(height-gutterBottom)){var linewidth=prop['chart.crosshairs.linewidth']?prop['chart.crosshairs.linewidth']:1;obj.context.lineWidth=linewidth?linewidth:1;obj.context.beginPath();obj.context.strokeStyle=prop['chart.crosshairs.color'];if(prop['chart.crosshairs.snap']){var point=null;var dist=null;var len=null;if(obj.type=='line'){for(var i=0;i<obj.coords.length;++i){var len=RGraph.getHypLength(obj.coords[i][0],obj.coords[i][1],x,y);if(typeof(dist)!='number'||len<dist){var point=i;var dist=len;}}
x=obj.coords[point][0];y=obj.coords[point][1];for(var dataset=0;dataset<obj.coords2.length;++dataset){for(var point=0;point<obj.coords2[dataset].length;++point){if(obj.coords2[dataset][point][0]==x&&obj.coords2[dataset][point][1]==y){obj.canvas.__crosshairs_snap_dataset__=dataset;obj.canvas.__crosshairs_snap_point__=point;}}}}else{for(var i=0;i<obj.coords.length;++i){for(var j=0;j<obj.coords[i].length;++j){var len=RGraph.getHypLength(obj.coords[i][j][0],obj.coords[i][j][1],x,y);if(typeof(dist)!='number'||len<dist){var dataset=i;var point=j;var dist=len;}}}
obj.canvas.__crosshairs_snap_dataset__=dataset;obj.canvas.__crosshairs_snap_point__=point;x=obj.coords[dataset][point][0];y=obj.coords[dataset][point][1];}}
if(prop['chart.crosshairs.vline']){obj.context.moveTo(Math.round(x),Math.round(gutterTop));obj.context.lineTo(Math.round(x),Math.round(height-gutterBottom));}
if(prop['chart.crosshairs.hline']){obj.context.moveTo(Math.round(gutterLeft),Math.round(y));obj.context.lineTo(Math.round(width-gutterRight),Math.round(y));}
obj.context.stroke();if(obj.type=='scatter'&&prop['chart.crosshairs.coords']){var xCoord=(((x-gutterLeft)/(width-gutterLeft-gutterRight))*(prop['chart.xmax']-prop['chart.xmin']))+prop['chart.xmin'];xCoord=xCoord.toFixed(prop['chart.scale.decimals']);var yCoord=obj.max-(((y-prop['chart.gutter.top'])/(height-gutterTop-gutterBottom))*obj.max);if(obj.type=='scatter'&&obj.properties['chart.xaxispos']=='center'){yCoord=(yCoord-(obj.max/2))*2;}
yCoord=yCoord.toFixed(prop['chart.scale.decimals']);var div=RGraph.Registry.Get('chart.coordinates.coords.div');var mouseXY=RGraph.getMouseXY(e);var canvasXY=RGraph.getCanvasXY(obj.canvas);if(!div){var div=document.createElement('DIV');div.__object__=obj;div.style.position='absolute';div.style.backgroundColor='white';div.style.border='1px solid black';div.style.fontFamily='Arial, Verdana, sans-serif';div.style.fontSize='10pt'
div.style.padding='2px';div.style.opacity=1;div.style.WebkitBorderRadius='3px';div.style.borderRadius='3px';div.style.MozBorderRadius='3px';document.body.appendChild(div);RGraph.Registry.Set('chart.coordinates.coords.div',div);}
div.style.opacity=1;div.style.display='inline';if(!prop['chart.crosshairs.coords.fixed']){div.style.left=Math.max(2,(e.pageX-div.offsetWidth-3))+'px';div.style.top=Math.max(2,(e.pageY-div.offsetHeight-3))+'px';}else{div.style.left=canvasXY[0]+gutterLeft+3+'px';div.style.top=canvasXY[1]+gutterTop+3+'px';}
div.innerHTML='<span style="color: #666">'+prop['chart.crosshairs.coords.labels.x']+':</span> '+xCoord+'<br><span style="color: #666">'+prop['chart.crosshairs.coords.labels.y']+':</span> '+yCoord;obj.canvas.addEventListener('mouseout',RGraph.HideCrosshairCoords,false);obj.canvas.__crosshairs_labels__=div;obj.canvas.__crosshairs_x__=xCoord;obj.canvas.__crosshairs_y__=yCoord;}else if(prop['chart.crosshairs.coords']){alert('[RGRAPH] Showing crosshair coordinates is only supported on the Scatter chart');}
RGraph.FireCustomEvent(obj,'oncrosshairs');}else{RGraph.HideCrosshairCoords();}}


if(typeof(RGraph)=='undefined')RGraph={isRGraph:true,type:'common'};__rgraph_resizing_preserve_css_properties__=[];RGraph.AllowResizing=function(obj)
{if(obj.Get('chart.resizable')){var canvas=obj.canvas;var context=obj.context;var resizeHandle=15;RGraph.Resizing.canvas=canvas;RGraph.Resizing.placeHolders=[];if(!canvas.__original_width__&&!canvas.__original_height__){canvas.__original_width__=canvas.width;canvas.__original_height__=canvas.height;}
var adjustX=(typeof(obj.Get('chart.resize.handle.adjust'))=='object'&&typeof(obj.Get('chart.resize.handle.adjust')[0])=='number'?obj.Get('chart.resize.handle.adjust')[0]:0);var adjustY=(typeof(obj.Get('chart.resize.handle.adjust'))=='object'&&typeof(obj.Get('chart.resize.handle.adjust')[1])=='number'?obj.Get('chart.resize.handle.adjust')[1]:0);var textWidth=context.measureText('Reset').width+2;var bgcolor=obj.Get('chart.resize.handle.background');if(!bgcolor){bgcolor='rgba(0,0,0,0)';}
context.beginPath();context.fillStyle=bgcolor;context.moveTo(canvas.width-resizeHandle-resizeHandle+adjustX,canvas.height-resizeHandle);context.rect(canvas.width-resizeHandle-resizeHandle+adjustX,canvas.height-resizeHandle+adjustY,2*resizeHandle,resizeHandle);context.fill();obj.context.beginPath();obj.context.strokeStyle='gray';obj.context.fillStyle='rgba(0,0,0,0)';obj.context.lineWidth=1;obj.context.moveTo(Math.round(obj.canvas.width-(resizeHandle/2)+adjustX),obj.canvas.height-resizeHandle+adjustY);obj.context.lineTo(Math.round(obj.canvas.width-(resizeHandle/2)+adjustX),obj.canvas.height+adjustY);obj.context.moveTo(obj.canvas.width+adjustX,Math.round(obj.canvas.height-(resizeHandle/2)+adjustY));obj.context.lineTo(obj.canvas.width-resizeHandle+adjustX,Math.round(obj.canvas.height-(resizeHandle/2)+adjustY));context.stroke();context.fill();context.fillStyle='gray';context.beginPath();context.moveTo(canvas.width-(resizeHandle/2)+adjustX,canvas.height-resizeHandle+adjustY);context.lineTo(canvas.width-(resizeHandle/2)+3+adjustX,canvas.height-resizeHandle+3+adjustY);context.lineTo(canvas.width-(resizeHandle/2)-3+adjustX,canvas.height-resizeHandle+3+adjustY);context.closePath();context.fill();context.beginPath();context.moveTo(canvas.width-(resizeHandle/2)+adjustX,canvas.height+adjustY);context.lineTo(canvas.width-(resizeHandle/2)+3+adjustX,canvas.height-3+adjustY);context.lineTo(canvas.width-(resizeHandle/2)-3+adjustX,canvas.height-3+adjustY);context.closePath();context.fill();context.beginPath();context.moveTo(canvas.width-resizeHandle+adjustX,canvas.height-(resizeHandle/2)+adjustY);context.lineTo(canvas.width-resizeHandle+3+adjustX,canvas.height-(resizeHandle/2)+3+adjustY);context.lineTo(canvas.width-resizeHandle+3+adjustX,canvas.height-(resizeHandle/2)-3+adjustY);context.closePath();context.fill();context.beginPath();context.moveTo(canvas.width+adjustX,canvas.height-(resizeHandle/2)+adjustY);context.lineTo(canvas.width-3+adjustX,canvas.height-(resizeHandle/2)+3+adjustY);context.lineTo(canvas.width-3+adjustX,canvas.height-(resizeHandle/2)-3+adjustY);context.closePath();context.fill();context.beginPath();context.fillStyle='white';context.moveTo(canvas.width+adjustX,canvas.height-(resizeHandle/2)+adjustY);context.rect(canvas.width-(resizeHandle/2)-2+adjustX,canvas.height-(resizeHandle/2)-2+adjustY,4,4);context.rect(canvas.width-(resizeHandle/2)-2+adjustX,canvas.height-(resizeHandle/2)-2+adjustY,4,4);context.stroke();context.fill();context.beginPath();context.fillStyle='gray';context.moveTo(Math.round(canvas.width-resizeHandle-3+adjustX),canvas.height-resizeHandle/2+adjustY);context.lineTo(Math.round(canvas.width-resizeHandle-resizeHandle+adjustX),canvas.height-(resizeHandle/2)+adjustY);context.lineTo(canvas.width-resizeHandle-resizeHandle+2+adjustX,canvas.height-(resizeHandle/2)-2+adjustY);context.lineTo(canvas.width-resizeHandle-resizeHandle+2+adjustX,canvas.height-(resizeHandle/2)+2+adjustY);context.lineTo(canvas.width-resizeHandle-resizeHandle+adjustX,canvas.height-(resizeHandle/2)+adjustY);context.stroke();context.fill();context.beginPath();context.moveTo(Math.round(canvas.width-resizeHandle-resizeHandle-1+adjustX),canvas.height-(resizeHandle/2)-3+adjustY);context.lineTo(Math.round(canvas.width-resizeHandle-resizeHandle-1+adjustX),canvas.height-(resizeHandle/2)+3+adjustY);context.stroke();context.fill();var window_onmousemove=function(e)
{e=RGraph.FixEventObject(e);var canvas=RGraph.Resizing.canvas;var newWidth=RGraph.Resizing.originalw-(RGraph.Resizing.originalx-e.pageX);var newHeight=RGraph.Resizing.originalh-(RGraph.Resizing.originaly-e.pageY);if(RGraph.Resizing.mousedown){if(newWidth>(canvas.__original_width__/2))RGraph.Resizing.div.style.width=newWidth+'px';if(newHeight>(canvas.__original_height__/2))RGraph.Resizing.div.style.height=newHeight+'px';RGraph.FireCustomEvent(canvas.__object__,'onresize');}}
if(typeof(canvas.rgraph_resize_window_mousemove_listener_installed)!='boolean'){window.addEventListener('mousemove',window_onmousemove,false);canvas.rgraph_resize_window_mousemove_listener_installed=true;}
var MouseupFunc=function(e)
{if(!RGraph.Resizing||!RGraph.Resizing.div||!RGraph.Resizing.mousedown){return;}
if(RGraph.Resizing.div){var div=RGraph.Resizing.div;var canvas=div.__canvas__;var coords=RGraph.getCanvasXY(div.__canvas__);var parentNode=canvas.parentNode;if(canvas.style.position!='absolute'){var placeHolderDIV=document.createElement('DIV');placeHolderDIV.style.width=RGraph.Resizing.originalw+'px';placeHolderDIV.style.height=RGraph.Resizing.originalh+'px';placeHolderDIV.style.display='inline-block';placeHolderDIV.style.position=canvas.style.position;placeHolderDIV.style.left=canvas.style.left;placeHolderDIV.style.top=canvas.style.top;placeHolderDIV.style.cssFloat=canvas.style.cssFloat;parentNode.insertBefore(placeHolderDIV,canvas);}
canvas.style.backgroundColor='white';canvas.style.position='absolute';canvas.style.border='1px dashed gray';canvas.style.left=(RGraph.Resizing.originalCanvasX-1)+'px';canvas.style.top=(RGraph.Resizing.originalCanvasY-1)+'px';canvas.width=parseInt(div.style.width);canvas.height=parseInt(div.style.height);canvas.getContext('2d').translate(0.5,0.5);RGraph.FireCustomEvent(canvas.__object__,'onresizebeforedraw');RGraph.RedrawCanvas(canvas);RGraph.Resizing.mousedown=false;div.style.display='none';document.body.removeChild(div);}
if(RGraph.Registry.Get('chart.zoomed.div')||RGraph.Registry.Get('chart.zoomed.img')){RGraph.Registry.Set('chart.zoomed.div',null);RGraph.Registry.Set('chart.zoomed.img',null);}
RGraph.FireCustomEvent(canvas.__object__,'onresizeend');}
var window_onmouseup=MouseupFunc;if(typeof(canvas.rgraph_resize_window_mouseup_listener_installed)!='boolean'){window.addEventListener('mouseup',window_onmouseup,false);canvas.rgraph_resize_window_mouseup_listener_installed=true;}
var canvas_onmousemove=function(e)
{e=RGraph.FixEventObject(e);var coords=RGraph.getMouseXY(e);var obj=e.target.__object__;var canvas=e.target;var context=canvas.getContext('2d');var cursor=canvas.style.cursor;if(!RGraph.Resizing.original_cursor){RGraph.Resizing.original_cursor=cursor;}
if((coords[0]>(canvas.width-resizeHandle)&&coords[0]<canvas.width&&coords[1]>(canvas.height-resizeHandle)&&coords[1]<canvas.height)){canvas.style.cursor='move';}else if(coords[0]>(canvas.width-resizeHandle-resizeHandle)&&coords[0]<canvas.width-resizeHandle&&coords[1]>(canvas.height-resizeHandle)&&coords[1]<canvas.height){canvas.style.cursor='pointer';}else{if(RGraph.Resizing.original_cursor){canvas.style.cursor=RGraph.Resizing.original_cursor;RGraph.Resizing.original_cursor=null;}else{canvas.style.cursor='default';}}}
if(typeof(canvas.rgraph_resize_mousemove_listener_installed)!='boolean'){canvas.addEventListener('mousemove',canvas_onmousemove,false);canvas.rgraph_resize_mousemove_listener_installed=true;}
var canvas_onmouseout=function(e)
{e.target.style.cursor='default';e.target.title='';}
if(typeof(canvas.rgraph_resize_mouseout_listener_installed)!='boolean'){canvas.addEventListener('mouseout',canvas_onmouseout,false);canvas.rgraph_resize_mouseout_listener_installed=true;}
var canvas_onmousedown=function(e)
{e=RGraph.FixEventObject(e);var coords=RGraph.getMouseXY(e);var canvasCoords=RGraph.getCanvasXY(e.target);var canvas=e.target;if(coords[0]>(obj.canvas.width-resizeHandle)&&coords[0]<obj.canvas.width&&coords[1]>(obj.canvas.height-resizeHandle)&&coords[1]<obj.canvas.height){RGraph.FireCustomEvent(obj,'onresizebegin');if(canvas.__original_css_border__==null){canvas.__original_css_border__=canvas.style.border;}
RGraph.Resizing.mousedown=true;var div=document.createElement('DIV');div.style.position='absolute';div.style.left=canvasCoords[0]+'px';div.style.top=canvasCoords[1]+'px';div.style.width=canvas.width+'px';div.style.height=canvas.height+'px';div.style.border='1px dotted black';div.style.backgroundColor='gray';div.style.opacity=0.5;div.__canvas__=e.target;document.body.appendChild(div);RGraph.Resizing.div=div;RGraph.Resizing.placeHolders.push(div);for(var i=0;i<(RGraph.Resizing.placeHolders.length-1);++i){RGraph.Resizing.placeHolders[i].style.display='none';}
div.onmouseup=function(e)
{MouseupFunc(e);}
RGraph.Resizing.div.onmouseover=function(e)
{e=RGraph.FixEventObject(e);e.stopPropagation();}
RGraph.Resizing.originalx=e.pageX;RGraph.Resizing.originaly=e.pageY;RGraph.Resizing.originalw=obj.canvas.width;RGraph.Resizing.originalh=obj.canvas.height;RGraph.Resizing.originalCanvasX=RGraph.getCanvasXY(obj.canvas)[0];RGraph.Resizing.originalCanvasY=RGraph.getCanvasXY(obj.canvas)[1];}
if(coords[0]>(canvas.width-resizeHandle-resizeHandle)&&coords[0]<canvas.width-resizeHandle&&coords[1]>(canvas.height-resizeHandle)&&coords[1]<canvas.height){RGraph.FireCustomEvent(canvas.__object__,'onresizebegin');canvas.width=canvas.__original_width__;canvas.height=canvas.__original_height__;canvas.style.border=canvas.__original_css_border__;canvas.style.left=(parseInt(canvas.style.left))+'px';canvas.style.top=(parseInt(canvas.style.top))+'px';canvas.getContext('2d').translate(0.5,0.5);RGraph.FireCustomEvent(canvas.__object__,'onresizebeforedraw');RGraph.Redraw();if(RGraph.Resizing.div){RGraph.Resizing.div.style.width=canvas.__original_width__+'px';RGraph.Resizing.div.style.height=canvas.__original_height__+'px';}
RGraph.FireCustomEvent(canvas.__object__,'onresize');RGraph.FireCustomEvent(canvas.__object__,'onresizeend');}}
if(typeof(canvas.rgraph_resize_mousedown_listener_installed)!='boolean'){canvas.addEventListener('mousedown',canvas_onmousedown,false);canvas.rgraph_resize_mousedown_listener_installed=true;}}}


if(typeof(RGraph)=='undefined')RGraph={isRGraph:true,type:'common'};RGraph.Contextmenu=function(obj,menuitems,e)
{var canvas=obj.canvas;e=RGraph.FixEventObject(e);RGraph.FireCustomEvent(obj,'onbeforecontextmenu');if(RGraph.Registry.Get('chart.contextmenu')){RGraph.HideContext();}
RGraph.HideZoomedCanvas();RGraph.HidePalette();obj.Set('chart.mousedown',false);var x=e.pageX;var y=e.pageY;var div=document.createElement('div');var bg=document.createElement('div');div.className='RGraph_contextmenu';div.__canvas__=canvas;div.style.position='absolute';div.style.left=0;div.style.top=0;div.style.border='1px solid black';div.style.backgroundColor='white';div.style.boxShadow='3px 3px 3px rgba(96,96,96,0.5)';div.style.MozBoxShadow='3px 3px 3px rgba(96,96,96,0.5)';div.style.WebkitBoxShadow='3px 3px 3px rgba(96,96,96,0.5)';div.style.filter='progid:DXImageTransform.Microsoft.Shadow(color=#aaaaaa,direction=135)';div.style.opacity=0;bg.className='RGraph_contextmenu_background';bg.style.position='absolute';bg.style.backgroundColor='#ccc';bg.style.borderRight='1px solid #aaa';bg.style.top=0;bg.style.left=0;bg.style.width='18px';bg.style.height='100%';bg.style.opacity=0;div=document.body.appendChild(div);bg=div.appendChild(bg);for(i=0;i<menuitems.length;++i){var menuitem=document.createElement('div');menuitem.__object__=obj;menuitem.__canvas__=canvas;menuitem.__contextmenu__=div;menuitem.className='RGraph_contextmenu_item';if(menuitems[i]){menuitem.style.padding='2px 5px 2px 23px';menuitem.style.fontFamily='Arial';menuitem.style.fontSize='10pt';menuitem.style.fontWeight='normal';menuitem.innerHTML=menuitems[i][0];if(RGraph.is_array(menuitems[i][1])){menuitem.style.backgroundImage='url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAcAAAAHCAYAAADEUlfTAAAAQUlEQVQImY3NoQ2AMABE0ZewABMyGQ6mqWODzlAclBSFO8HZl8uf0FFxCHtwYkt4Y6ChYE44cGH9/fyae2p2LAleW9oVTQuVf6gAAAAASUVORK5CYII=)';menuitem.style.backgroundRepeat='no-repeat';menuitem.style.backgroundPosition='97% center';}
if(menuitems[i][1]){if(menuitem.addEventListener){menuitem.addEventListener("mouseover",function(e){RGraph.HideContextSubmenu();e.target.style.backgroundColor='rgba(0,0,0,0.2)';e.target.style.cursor='pointer';},false);menuitem.addEventListener("mouseout",function(e){e.target.style.backgroundColor='inherit';e.target.style.cursor='default';},false);}else{menuitem.attachEvent("onmouseover",function(){RGraph.HideContextSubmenu();event.srcElement.style.backgroundColor='#eee';event.srcElement.style.cursor='pointer';},false);menuitem.attachEvent("onmouseout",function(){event.srcElement.style.backgroundColor='inherit';event.srcElement.style.cursor='default';},false);}}else{if(menuitem.addEventListener){menuitem.addEventListener("mouseover",function(e){e.target.style.cursor='default';},false);menuitem.addEventListener("mouseout",function(e){e.target.style.cursor='default';},false);}else{menuitem.attachEvent("onmouseover",function(){event.srcElement.style.cursor='default'},false);menuitem.attachEvent("onmouseout",function(){event.srcElement.style.cursor='default';},false);}}}else{menuitem.style.borderBottom='1px solid #ddd';menuitem.style.marginLeft='25px';}
div.appendChild(menuitem);if(menuitems[i]&&menuitems[i][1]&&typeof(menuitems[i][1])=='function'){menuitem.addEventListener('click',menuitems[i][1],false);}else if(menuitems[i]&&menuitems[i][1]&&RGraph.is_array(menuitems[i][1])){(function()
{var tmp=menuitems[i][1];menuitem.addEventListener('mouseover',function(e){RGraph.Contextmenu_submenu(obj,tmp,e.target);},false);})();}}
div.style.width=(div.offsetWidth+10)+'px';div.style.height=(div.offsetHeight-2)+'px';if(x+div.offsetWidth>document.body.offsetWidth){x-=div.offsetWidth;}
div.style.left=x+'px';div.style.top=y+'px';setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu')) obj.style.opacity = 0.2",50);setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu')) obj.style.opacity = 0.4",100);setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu')) obj.style.opacity = 0.6",150);setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu')) obj.style.opacity = 0.8",200);setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu')) obj.style.opacity = 1",250);setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu.bg')) obj.style.opacity = 0.2",50);setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu.bg')) obj.style.opacity = 0.4",100);setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu.bg')) obj.style.opacity = 0.6",150);setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu.bg')) obj.style.opacity = 0.8",200);setTimeout("if (obj = RGraph.Registry.Get('chart.contextmenu.bg')) obj.style.opacity = 1",250);RGraph.Registry.Set('chart.contextmenu',div);RGraph.Registry.Set('chart.contextmenu.bg',bg);RGraph.Registry.Get('chart.contextmenu').oncontextmenu=function(){return false;};RGraph.Registry.Get('chart.contextmenu.bg').oncontextmenu=function(){return false;};canvas.addEventListener('click',function(){RGraph.HideContext();},false);window.addEventListener('click',function()
{RGraph.HideContext();},false);window.addEventListener('resize',function()
{RGraph.HideContext();},false);if(typeof(obj.getShape)=='function'){RGraph.Registry.Get('chart.contextmenu').__shape__=obj.getShape(e);}
e.stopPropagation();RGraph.FireCustomEvent(obj,'oncontextmenu');return false;}
RGraph.HideContext=function()
{var cm=RGraph.Registry.Get('chart.contextmenu');var cmbg=RGraph.Registry.Get('chart.contextmenu.bg');RGraph.HideContextSubmenu();if(cm){cm.parentNode.removeChild(cm);cmbg.parentNode.removeChild(cmbg);cm.style.visibility='hidden';cm.style.display='none';RGraph.Registry.Set('chart.contextmenu',null);cmbg.style.visibility='hidden';cmbg.style.display='none';RGraph.Registry.Set('chart.contextmenu.bg',null);}}
RGraph.HideContextSubmenu=function()
{var sub=RGraph.Registry.Get('chart.contextmenu.submenu');if(sub){sub.style.visibility='none';sub.style.display='none';RGraph.Registry.Set('chart.contextmenu.submenu',null);}}
RGraph.ShowContext=function(obj)
{RGraph.HidePalette();if(obj.Get('chart.contextmenu')&&obj.Get('chart.contextmenu').length){var isOpera=navigator.userAgent.indexOf('Opera')>=0;var isSafari=navigator.userAgent.indexOf('Safari')>=0;var isChrome=navigator.userAgent.indexOf('Chrome')>=0;var isMacFirefox=navigator.userAgent.indexOf('Firefox')>0&&navigator.userAgent.indexOf('Mac')>0;var isIE9=navigator.userAgent.indexOf('MSIE 9')>=0;if(((!isOpera&&!isSafari)||isChrome)&&!isMacFirefox){obj.canvas.oncontextmenu=function(e)
{e=RGraph.FixEventObject(e);if(e.ctrlKey)return true;RGraph.Contextmenu(obj,obj.Get('chart.contextmenu'),e);return false;}}else{obj.canvas.addEventListener('dblclick',function(e)
{if(e.ctrlKey)return true;if(!RGraph.Registry.Get('chart.contextmenu')){RGraph.Contextmenu(obj,obj.Get('chart.contextmenu'),e);}},false);}}}
RGraph.Contextmenu_submenu=function(obj,menuitems,parentMenuItem)
{RGraph.HideContextSubmenu();var canvas=obj.canvas;var context=obj.context;var menu=parentMenuItem.parentNode;var subMenu=document.createElement('DIV');subMenu.style.position='absolute';subMenu.style.width='100px';subMenu.style.top=menu.offsetTop+parentMenuItem.offsetTop+'px';subMenu.style.left=(menu.offsetLeft+menu.offsetWidth-(RGraph.isOld()?9:0))+'px';subMenu.style.backgroundColor='white';subMenu.style.border='1px solid black';subMenu.className='RGraph_contextmenu';subMenu.__contextmenu__=menu;subMenu.style.boxShadow='3px 3px 3px rgba(96,96,96,0.5)';subMenu.style.MozBoxShadow='3px 3px 3px rgba(96,96,96,0.5)';subMenu.style.WebkitBoxShadow='3px 3px 3px rgba(96,96,96,0.5)';subMenu.style.filter='progid:DXImageTransform.Microsoft.Shadow(color=#aaaaaa,direction=135)';document.body.appendChild(subMenu);for(var i=0;i<menuitems.length;++i){var menuitem=document.createElement('DIV');menuitem.__canvas__=canvas;menuitem.__contextmenu__=menu;menuitem.className='RGraph_contextmenu_item';if(menuitems[i]){menuitem.style.padding='2px 5px 2px 23px';menuitem.style.fontFamily='Arial';menuitem.style.fontSize='10pt';menuitem.style.fontWeight='normal';menuitem.innerHTML=menuitems[i][0];if(menuitems[i][1]){if(menuitem.addEventListener){menuitem.addEventListener("mouseover",function(e){e.target.style.backgroundColor='rgba(0,0,0,0.2)';e.target.style.cursor='pointer';},false);menuitem.addEventListener("mouseout",function(e){e.target.style.backgroundColor='inherit';e.target.style.cursor='default';},false);}else{menuitem.attachEvent("onmouseover",function(){event.srcElement.style.backgroundColor='rgba(0,0,0,0.2)';event.srcElement.style.cursor='pointer'},false);menuitem.attachEvent("onmouseout",function(){event.srcElement.style.backgroundColor='inherit';event.srcElement.style.cursor='default';},false);}}else{if(menuitem.addEventListener){menuitem.addEventListener("mouseover",function(e){e.target.style.cursor='default';},false);menuitem.addEventListener("mouseout",function(e){e.target.style.cursor='default';},false);}else{menuitem.attachEvent("onmouseover",function(){event.srcElement.style.cursor='default'},false);menuitem.attachEvent("onmouseout",function(){event.srcElement.style.cursor='default';},false);}}}else{menuitem.style.borderBottom='1px solid #ddd';menuitem.style.marginLeft='25px';}
subMenu.appendChild(menuitem);if(menuitems[i]&&menuitems[i][1]){if(document.all){menuitem.attachEvent('onclick',menuitems[i][1]);}else{menuitem.addEventListener('click',menuitems[i][1],false);}}}
var bg=document.createElement('DIV');bg.className='RGraph_contextmenu_background';bg.style.position='absolute';bg.style.backgroundColor='#ccc';bg.style.borderRight='1px solid #aaa';bg.style.top=0;bg.style.left=0;bg.style.width='18px';bg.style.height='100%';bg=subMenu.appendChild(bg);RGraph.Registry.Set('chart.contextmenu.submenu',subMenu);}
RGraph.showPNG=function()
{if(RGraph.isIE8()){alert('[RGRAPH PNG] Sorry, showing a PNG is not supported on MSIE8.');return;}
if(arguments[0]&&arguments[0].id){var canvas=arguments[0];var event=arguments[1];}else if(RGraph.Registry.Get('chart.contextmenu')){var canvas=RGraph.Registry.Get('chart.contextmenu').__canvas__;}else{alert('[RGRAPH SHOWPNG] Could not find canvas!');}
var obj=canvas.__object__;var bg=document.createElement('DIV');bg.id='__rgraph_image_bg__';bg.style.position='fixed';bg.style.top='-10px';bg.style.left='-10px';bg.style.width='5000px';bg.style.height='5000px';bg.style.backgroundColor='rgb(204,204,204)';bg.style.opacity=0;document.body.appendChild(bg);var div=document.createElement('DIV');div.style.backgroundColor='white';div.style.opacity=0;div.style.border='1px solid black';div.style.position='fixed';div.style.top='20%';div.style.width=canvas.width+'px';div.style.height=canvas.height+35+'px';div.style.left=(document.body.clientWidth/2)-(canvas.width/2)+'px';div.style.padding='5px';div.style.borderRadius='10px';div.style.MozBorderRadius='10px';div.style.WebkitBorderRadius='10px';div.style.boxShadow='0 0 15px rgba(96,96,96,0.5)';div.style.MozBoxShadow='0 0 15px rgba(96,96,96,0.5)';div.style.WebkitBoxShadow='rgba(96,96,96,0.5) 0 0 15px';div.__canvas__=canvas;div.__object__=obj;div.id='__rgraph_image_div__';document.body.appendChild(div);div.innerHTML+='<div style="position: absolute; margin-left: 10px; top: '+canvas.height+'px; width: '+(canvas.width-50)+'px; height: 25px"><span style="font-size: 12pt;display: inline; display: inline-block; width: 65px; text-align: right">URL:</span><textarea style="float: right; overflow: hidden; height: 20px; width: '+(canvas.width-obj.gutterLeft-obj.gutterRight-80)+'px" onclick="this.select()" readonly="readonly" id="__rgraph_dataurl__">'+canvas.toDataURL()+'</textarea></div>';div.innerHTML+='<div style="position: absolute; top: '+(canvas.height+25)+'px; left: '+(obj.gutterLeft-65+(canvas.width/2))+'px; width: '+(canvas.width-obj.gutterRight)+'px; font-size: 65%">A link using the URL: <a href="'+canvas.toDataURL()+'">View</a></div>'
var img=document.createElement('IMG');RGraph.Registry.Set('chart.png',img);img.__canvas__=canvas;img.__object__=obj;img.id='__rgraph_image_img__';img.className='RGraph_png';img.src=canvas.toDataURL();div.appendChild(img);setTimeout(function(){document.getElementById("__rgraph_dataurl__").select();},50);window.addEventListener('resize',function(e){var img=RGraph.Registry.Get('chart.png');img.style.left=(document.body.clientWidth/2)-(img.width/2)+'px';},false);bg.onclick=function(e)
{var div=document.getElementById("__rgraph_image_div__");var bg=document.getElementById("__rgraph_image_bg__");if(div){div.style.opacity=0;div.parentNode.removeChild(div);div.id='';div.style.display='none';div=null;}
if(bg){bg.style.opacity=0;bg.id='';bg.style.display='none';bg=null;}}
window.addEventListener('resize',function(e){bg.onclick(e);},false)
__rgraph_image_bg__=bg;__rgraph_image_div__=div;setTimeout('__rgraph_image_div__.style.opacity = 0.2',50);setTimeout('__rgraph_image_div__.style.opacity = 0.4',100);setTimeout('__rgraph_image_div__.style.opacity = 0.6',150);setTimeout('__rgraph_image_div__.style.opacity = 0.8',200);setTimeout('__rgraph_image_div__.style.opacity = 1',250);setTimeout('__rgraph_image_bg__.style.opacity = 0.1',50);setTimeout('__rgraph_image_bg__.style.opacity = 0.2',100);setTimeout('__rgraph_image_bg__.style.opacity = 0.3',150);setTimeout('__rgraph_image_bg__.style.opacity = 0.4',200);setTimeout('__rgraph_image_bg__.style.opacity = 0.5',250);img.onclick=function(e)
{if(e.stopPropagation)e.stopPropagation();else event.cancelBubble=true;}
if(event&&event.stopPropagation){event.stopPropagation();}}


if(typeof(RGraph)=='undefined')RGraph={};RGraph.CornerGauge=function(id,min,max,value)
{this.id=id;this.canvas=document.getElementById(id);this.context=this.canvas.getContext?this.canvas.getContext("2d"):null;this.canvas.__object__=this;this.type='cornergauge';this.min=min;this.max=max;this.value=value;this.angles={};this.angles.needle=[];this.centerpin={};this.isRGraph=true;this.currentValue=null;this.uid=RGraph.CreateUID();this.canvas.uid=this.canvas.uid?this.canvas.uid:RGraph.CreateUID();this.coordsText=[];if(typeof(this.value)=='object'){for(var i=0;i<this.value.length;++i){if(this.value[i]>this.max)this.value[i]=max;if(this.value[i]<this.min)this.value[i]=min;}}else{if(this.value>this.max)this.value=max;if(this.value<this.min)this.value=min;}
RGraph.OldBrowserCompat(this.context);this.properties={'chart.centerx':null,'chart.centery':null,'chart.radius':null,'chart.gutter.left':25,'chart.gutter.right':25,'chart.gutter.top':25,'chart.gutter.bottom':25,'chart.strokestyle':'black','chart.linewidth':2,'chart.title':'','chart.title.vpos':0.5,'chart.title.size':null,'chart.title.x':null,'chart.title.y':null,'chart.title.bold':true,'chart.text.font':'Arial','chart.text.color':'#666','chart.text.size':10,'chart.background.gradient.color1':'#ddd','chart.background.gradient.color2':'white','chart.shadow':true,'chart.shadow.color':'gray','chart.shadow.offsetx':0,'chart.shadow.offsety':0,'chart.shadow.blur':15,'chart.scale.decimals':0,'chart.scale.point':'.','chart.scale.thousand':',','chart.units.pre':'','chart.units.post':'','chart.resizable':false,'chart.chart.resize.handle.background':null,'chart.adjustable':false,'chart.annotatable':false,'chart.annotate.color':'black','chart.colors.ranges':null,'chart.red.start':min+(0.9*(this.max-min)),'chart.green.end':min+(0.7*(this.max-min)),'chart.red.color':'red','chart.yellow.color':'yellow','chart.green.color':'#0f0','chart.value.text':true,'chart.value.text.units.pre':'','chart.value.text.units.post':'','chart.value.text.boxed':true,'chart.value.text.font':'Arial','chart.value.text.size':18,'chart.value.text.bold':false,'chart.value.text.decimals':0,'chart.centerpin.stroke':'rgba(0,0,0,0)','chart.centerpin.fill':null,'chart.centerpin.color':'blue','chart.needle.colors':['#ccc','#D5604D','red','green','yellow'],'chart.zoom.factor':1.5,'chart.zoom.fade.in':true,'chart.zoom.fade.out':true,'chart.zoom.hdir':'right','chart.zoom.vdir':'down','chart.zoom.frames':25,'chart.zoom.delay':16.666,'chart.zoom.shadow':true,'chart.zoom.background':true}
if(!this.canvas.__rgraph_aa_translated__){this.context.translate(0.5,0.5);this.canvas.__rgraph_aa_translated__=true;}
var RG=RGraph;var ca=this.canvas;var co=ca.getContext('2d');var prop=this.properties;this.Set=function(name,value)
{name=name.toLowerCase();if(name.substr(0,6)!='chart.'){name='chart.'+name;}
prop[name]=value;return this;}
this.Get=function(name)
{if(name.substr(0,6)!='chart.'){name='chart.'+name;}
return prop[name];}
this.Draw=function()
{RG.FireCustomEvent(this,'onbeforedraw');this.currentValue=this.value;this.gutterLeft=prop['chart.gutter.left'];this.gutterRight=prop['chart.gutter.right'];this.gutterTop=prop['chart.gutter.top'];this.gutterBottom=prop['chart.gutter.bottom'];this.radius=Math.min((ca.width-this.gutterLeft-this.gutterRight),(ca.height-this.gutterTop-this.gutterBottom));if(typeof(prop['chart.radius'])=='number')this.radius=prop['chart.radius'];this.centerx=(ca.width/2)-(this.radius/2)+Math.max(30,this.radius*0.1);this.centery=(ca.height/2)+(this.radius/2)-(this.radius*0.1);if(typeof(prop['chart.centerx'])=='number')this.centerx=prop['chart.centerx'];if(typeof(prop['chart.centery'])=='number')this.centery=prop['chart.centery'];if(!this.colorsParsed){this.parseColors();this.colorsParsed=true;}
this.DrawBackGround();this.DrawTickmarks();this.DrawColorBands();this.DrawLabel();this.DrawLabels();if(typeof(this.value)=='object'){for(var i=0,len=this.value.length;i<len;++i){this.DrawNeedle(i,this.value[i],this.radius-65);}}else{this.DrawNeedle(0,this.value,this.radius-65);}
this.DrawCenterpin();var size=prop['chart.title.size']?prop['chart.title.size']:prop['chart.text.size']+2
prop['chart.title.y']=this.centery+20-this.radius-((1.5*size)/2);RGraph.DrawTitle(this,prop['chart.title'],this.guttertop,this.centerx+(this.radius/2),size);if(prop['chart.contextmenu']){RGraph.ShowContext(this);}
if(prop['chart.resizable']){RGraph.AllowResizing(this);}
RGraph.InstallEventListeners(this);RGraph.FireCustomEvent(this,'ondraw');}
this.DrawBackGround=function()
{if(prop['chart.shadow']){RGraph.SetShadow(this,prop['chart.shadow.color'],prop['chart.shadow.offsetx'],prop['chart.shadow.offsety'],prop['chart.shadow.blur']);}
co.strokeStyle=prop['chart.strokestyle'];co.lineWidth=prop['chart.linewidth']?prop['chart.linewidth']:0.0001;co.beginPath();co.arc(this.centerx,this.centery,30,0,TWOPI,false);co.stroke();co.beginPath();co.moveTo(this.centerx-20,this.centery+20);co.arc(this.centerx-20,this.centery+20,this.radius,PI+HALFPI,TWOPI,false);co.closePath();co.fill();co.stroke();RG.NoShadow(this);co.strokeStyle=prop['chart.strokestyle'];co.lineWidth=prop['chart.linewidth']?prop['chart.linewidth']:0.0001;co.beginPath();co.moveTo(this.centerx-20,this.centery+20);co.arc(this.centerx-20,this.centery+20,this.radius,PI+HALFPI,TWOPI,false);co.closePath();co.stroke();RGraph.NoShadow(this);co.lineWidth=0;co.fillStyle=RGraph.RadialGradient(this,this.centerx,this.centery,0,this.centerx,this.centery,this.radius*0.5,prop['chart.background.gradient.color1'],prop['chart.background.gradient.color2']);co.beginPath();co.moveTo(this.centerx,this.centery);co.arc(this.centerx,this.centery,30,0,TWOPI,0);co.closePath();co.fill();co.beginPath();co.moveTo(this.centerx-20,this.centery+20);co.lineTo(this.centerx-20,this.centery+20-this.radius);co.arc(this.centerx-20,this.centery+20,this.radius,PI+HALFPI,TWOPI,false);co.closePath();co.fill();co.beginPath();co.lineWidth=1;co.strokeStyle='#eee';for(var i=0;i<=5;++i){var p1=RG.getRadiusEndPoint(this.centerx,this.centery,(HALFPI/5*i)+PI+HALFPI,30);var p2=RG.getRadiusEndPoint(this.centerx,this.centery,(HALFPI/5*i)+PI+HALFPI,this.radius-90);co.moveTo(p1[0],p1[1]);co.lineTo(p2[0],p2[1]);}
co.stroke();}
this.DrawNeedle=function(index,value,radius)
{var grad=RG.RadialGradient(this,this.centerx,this.centery,0,this.centerx,this.centery,20,'rgba(0,0,0,0)',prop['chart.needle.colors'][index])
this.angles.needle[index]=(((value-this.min)/(this.max-this.min))*HALFPI)+PI+HALFPI;co.lineWidth=1
co.strokeStyle='rgba(0,0,0,0)';co.fillStyle=grad;co.beginPath();co.moveTo(this.centerx,this.centery);co.arc(this.centerx,this.centery,10,this.angles.needle[index]-HALFPI,this.angles.needle[index]-HALFPI+0.000001,false);co.arc(this.centerx,this.centery,radius-30,this.angles.needle[index],this.angles.needle[index]+0.000001,false);co.arc(this.centerx,this.centery,10,this.angles.needle[index]+HALFPI,this.angles.needle[index]+HALFPI+0.000001,false);co.stroke();co.fill();}
this.DrawCenterpin=function()
{if(!prop['chart.centerpin.fill']){prop['chart.centerpin.fill']=RG.RadialGradient(this,this.centerx+5,this.centery-5,0,this.centerx+5,this.centery-5,20,'white',prop['chart.centerpin.color'])}
co.strokeStyle=prop['chart.centerpin.stroke'];co.fillStyle=prop['chart.centerpin.fill'];co.beginPath();co.lineWidth=2;co.arc(this.centerx,this.centery,15,0,TWOPI,false);co.stroke();co.fill();}
this.DrawLabels=function()
{var numLabels=5;co.fillStyle=prop['chart.text.color'];for(var i=0;i<numLabels;++i){co.beginPath();var num=Number(this.min+((this.max-this.min)*(i/(numLabels-1)))).toFixed(prop['chart.scale.decimals']);num=RG.number_format(this,num,prop['chart.units.pre'],prop['chart.units.post']);var angle=(i*22.5)/(180/PI);RG.Text2(this,{'font':prop['chart.text.font'],'size':prop['chart.text.size'],'x':this.centerx+Math.sin(angle)*(this.radius-53),'y':this.centery-Math.cos(angle)*(this.radius-53),'text':String(num),'valign':'top','halign':'center','angle':90*(i/(numLabels-1)),'tag':'scale'});co.fill();}}
this.DrawTickmarks=function()
{var bigTicks=5;var smallTicks=25;for(var i=0;i<smallTicks;++i){co.beginPath();var angle=(HALFPI/(smallTicks-1))*i
co.lineWidth=1;co.arc(this.centerx,this.centery,this.radius-44,PI+HALFPI+angle,PI+HALFPI+angle+0.0001,false);co.arc(this.centerx,this.centery,this.radius-46,PI+HALFPI+angle,PI+HALFPI+angle+0.0001,false);co.stroke();}
for(var i=0;i<bigTicks;++i){co.beginPath();var angle=(HALFPI/(bigTicks-1))*i
co.lineWidth=1;co.arc(this.centerx,this.centery,this.radius-43,PI+HALFPI+angle,PI+HALFPI+angle+0.0001,false);co.arc(this.centerx,this.centery,this.radius-47,PI+HALFPI+angle,PI+HALFPI+angle+0.0001,false);co.stroke();}}
this.DrawColorBands=function()
{if(RG.is_array(prop['chart.colors.ranges'])){var ranges=prop['chart.colors.ranges'];for(var i=0,len=ranges.length;i<len;++i){co.fillStyle=ranges[i][2];co.lineWidth=0;co.beginPath();co.arc(this.centerx,this.centery,this.radius-54-(prop['chart.text.size']*1.5),(((ranges[i][0]-this.min)/(this.max-this.min))*HALFPI)+(PI+HALFPI),(((ranges[i][1]-this.min)/(this.max-this.min))*HALFPI)+(PI+HALFPI),false);co.arc(this.centerx,this.centery,this.radius-54-10-(prop['chart.text.size']*1.5),(((ranges[i][1]-this.min)/(this.max-this.min))*HALFPI)+(PI+HALFPI),(((ranges[i][0]-this.min)/(this.max-this.min))*HALFPI)+(PI+HALFPI),true);co.closePath();co.fill();}
return;}
co.strokeStyle=prop['chart.green.color'];co.fillStyle=prop['chart.green.color'];var greenStart=PI+HALFPI;var greenEnd=greenStart+(TWOPI-greenStart)*((prop['chart.green.end']-this.min)/(this.max-this.min))
co.beginPath();co.arc(this.centerx,this.centery,this.radius-54-(prop['chart.text.size']*1.5),greenStart,greenEnd,false);co.arc(this.centerx,this.centery,this.radius-54-(prop['chart.text.size']*1.5)-10,greenEnd,greenStart,true);co.fill();co.strokeStyle=prop['chart.yellow.color'];co.fillStyle=prop['chart.yellow.color'];var yellowStart=greenEnd;var yellowEnd=(((prop['chart.red.start']-this.min)/(this.max-this.min))*HALFPI)+PI+HALFPI;co.beginPath();co.arc(this.centerx,this.centery,this.radius-54-(prop['chart.text.size']*1.5),yellowStart,yellowEnd,false);co.arc(this.centerx,this.centery,this.radius-54-(prop['chart.text.size']*1.5)-10,yellowEnd,yellowStart,true);co.fill();co.strokeStyle=prop['chart.red.color'];co.fillStyle=prop['chart.red.color'];var redStart=yellowEnd;var redEnd=TWOPI;co.beginPath();co.arc(this.centerx,this.centery,this.radius-54-(prop['chart.text.size']*1.5),redStart,redEnd,false);co.arc(this.centerx,this.centery,this.radius-54-(prop['chart.text.size']*1.5)-10,redEnd,redStart,true);co.fill();}
this.DrawLabel=function()
{if(prop['chart.value.text']){co.strokeStyle=prop['chart.text.color'];co.fillStyle=prop['chart.text.color'];var value=typeof(this.value)=='number'?this.value.toFixed(prop['chart.value.text.decimals']):this.value;if(typeof(value)=='object'){for(var i=0;i<value.length;++i){value[i]=Number(value[i]).toFixed(prop['chart.value.text.decimals']);}
value=value.toString();}
RG.Text2(this,{'font':prop['chart.value.text.font'],'size':prop['chart.value.text.size'],'x':this.centerx+(Math.cos((PI/180)*45)*(this.radius/3)),'y':this.centery-(Math.sin((PI/180)*45)*(this.radius/3)),'text':RG.number_format(this,String(value),prop['chart.value.text.units.pre'],prop['chart.value.text.units.post']),'valign':'center','halign':'center','bounding':prop['chart.value.text.boxed'],'boundingFill':'white','bold':prop['chart.value.text.bold'],'tag':'value.text'});}}
this.getShape=function(e)
{}
this.getValue=function(e)
{var mouseXY=RGraph.getMouseXY(e);var mouseX=mouseXY[0];var mouseY=mouseXY[1];var angle=RG.getAngleByXY(this.centerx,this.centery,mouseX,mouseY);if(angle>TWOPI&&angle<(PI+HALFPI)){return null;}
var value=((angle-(PI+HALFPI))/(TWOPI-(PI+HALFPI)))*(this.max-this.min);value=value+this.min;if(value<this.min){value=this.min}
if(value>this.max){value=this.max}
if(mouseX>this.centerx&&mouseY>this.centery){value=this.max;}
return value;}
this.getObjectByXY=function(e)
{var mouseXY=RGraph.getMouseXY(e);if(mouseXY[0]>(this.centerx-5)&&mouseXY[0]<(this.centerx+this.radius)&&mouseXY[1]>(this.centery-this.radius)&&mouseXY[1]<(this.centery+5)&&RG.getHypLength(this.centerx,this.centery,mouseXY[0],mouseXY[1])<=this.radius){return this;}}
this.Adjusting_mousemove=function(e)
{if(prop['chart.adjustable']&&RG.Registry.Get('chart.adjusting')&&RG.Registry.Get('chart.adjusting').uid==this.uid){this.value=this.getValue(e);RG.Clear(ca);RG.RedrawCanvas(ca);RG.FireCustomEvent(this,'onadjust');}}
this.getAngle=function(value)
{if(value<this.min||value>this.max){return null;}
var angle=((value-this.min)/(this.max-this.min))*HALFPI
angle+=(PI+HALFPI);return angle;}
this.parseColors=function()
{if(!RG.is_null(prop['chart.colors.ranges'])){for(var i=0;i<prop['chart.colors.ranges'].length;++i){prop['chart.colors.ranges'][i][2]=this.parseSingleColorForGradient(prop['chart.colors.ranges'][i][2]);}}else{prop['chart.green.color']=this.parseSingleColorForGradient(prop['chart.green.color']);prop['chart.yellow.color']=this.parseSingleColorForGradient(prop['chart.yellow.color']);prop['chart.red.color']=this.parseSingleColorForGradient(prop['chart.red.color']);}}
this.parseSingleColorForGradient=function(color)
{if(!color||typeof(color)!='string'){return color;}
if(color.match(/^gradient\((.*)\)$/i)){var parts=RegExp.$1.split(':');var radius_start=this.radius-54-prop['chart.text.size'];var radius_end=radius_start-15;var grad=co.createRadialGradient(this.centerx,this.centery,radius_start,this.centerx,this.centery,radius_end);var diff=1/(parts.length-1);grad.addColorStop(0,RG.trim(parts[0]));for(var j=1,len=parts.length;j<len;++j){grad.addColorStop(j*diff,RG.trim(parts[j]));}}
return grad?grad:color;}
RG.Register(this);}

