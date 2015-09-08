<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'test.jsp' starting page</title>

<link href="<%=basePath%>bootstrap/css/style.default.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>bootstrap/css/jquery.tagsinput.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>bootstrap/css/toggles.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>bootstrap/css/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>bootstrap/css/select2.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>bootstrap/css/colorpicker.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>bootstrap/css/dropzone.css" rel="stylesheet" type="text/css"/>

</head>

<body>
	<div class="panel-body">

		<br> <label>Default Functionality</label>
		<div class="input-group">
			<input type="text" class="form-control hasDatepicker"
				placeholder="mm/dd/yyyy" id="datepicker"> <span
				class="input-group-addon"><i
				class="glyphicon glyphicon-calendar"></i>
			</span>
		</div>
		<!-- input-group -->

		<br>

		<p>
			Set the
			<code>numberOfMonths</code>
			option to an integer of 2 or more to show multiple months in a single
			datepicker.
		</p>
		<br> <label>Multiple Months</label>
		<div class="input-group">
			<input type="text" class="form-control hasDatepicker"
				placeholder="mm/dd/yyyy" id="datepicker-multiple"> <span
				class="input-group-addon"><i
				class="glyphicon glyphicon-calendar"></i>
			</span>
		</div>
		<!-- input-group -->

		<br> <label>Display Inline</label>
		<div class="input-group mb15">
			<div id="datepicker-inline" class="hasDatepicker">
				<div
					class="ui-datepicker-inline ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"
					style="display: block;">
					<div
						class="ui-datepicker-header ui-widget-header ui-helper-clearfix ui-corner-all">
						<a class="ui-datepicker-prev ui-corner-all" data-handler="prev"
							data-event="click" title="Prev"><span
							class="ui-icon ui-icon-circle-triangle-w">Prev</span>
						</a><a class="ui-datepicker-next ui-corner-all" data-handler="next"
							data-event="click" title="Next"><span
							class="ui-icon ui-icon-circle-triangle-e">Next</span>
						</a>
						<div class="ui-datepicker-title">
							<span class="ui-datepicker-month">January</span>&nbsp;<span
								class="ui-datepicker-year">2015</span>
						</div>
					</div>
					<table class="ui-datepicker-calendar">
						<thead>
							<tr>
								<th class="ui-datepicker-week-end"><span title="Sunday">Su</span>
								</th>
								<th><span title="Monday">Mo</span>
								</th>
								<th><span title="Tuesday">Tu</span>
								</th>
								<th><span title="Wednesday">We</span>
								</th>
								<th><span title="Thursday">Th</span>
								</th>
								<th><span title="Friday">Fr</span>
								</th>
								<th class="ui-datepicker-week-end"><span title="Saturday">Sa</span>
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td
									class=" ui-datepicker-week-end ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">&nbsp;</td>
								<td
									class=" ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">&nbsp;</td>
								<td
									class=" ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">&nbsp;</td>
								<td
									class=" ui-datepicker-other-month ui-datepicker-unselectable ui-state-disabled">&nbsp;</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">1</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">2</a>
								</td>
								<td class=" ui-datepicker-week-end " data-handler="selectDay"
									data-event="click" data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">3</a>
								</td>
							</tr>
							<tr>
								<td class=" ui-datepicker-week-end " data-handler="selectDay"
									data-event="click" data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">4</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">5</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">6</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">7</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">8</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">9</a>
								</td>
								<td class=" ui-datepicker-week-end " data-handler="selectDay"
									data-event="click" data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">10</a>
								</td>
							</tr>
							<tr>
								<td class=" ui-datepicker-week-end " data-handler="selectDay"
									data-event="click" data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">11</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">12</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">13</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">14</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">15</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">16</a>
								</td>
								<td class=" ui-datepicker-week-end " data-handler="selectDay"
									data-event="click" data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">17</a>
								</td>
							</tr>
							<tr>
								<td class=" ui-datepicker-week-end " data-handler="selectDay"
									data-event="click" data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">18</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">19</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">20</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">21</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">22</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">23</a>
								</td>
								<td class=" ui-datepicker-week-end " data-handler="selectDay"
									data-event="click" data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">24</a>
								</td>
							</tr>
							<tr>
								<td class=" ui-datepicker-week-end " data-handler="selectDay"
									data-event="click" data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">25</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">26</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">27</a>
								</td>
								<td
									class=" ui-datepicker-days-cell-over  ui-datepicker-current-day ui-datepicker-today"
									data-handler="selectDay" data-event="click" data-month="0"
									data-year="2015"><a
									class="ui-state-default ui-state-highlight ui-state-active"
									href="#">28</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">29</a>
								</td>
								<td class=" " data-handler="selectDay" data-event="click"
									data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">30</a>
								</td>
								<td class=" ui-datepicker-week-end " data-handler="selectDay"
									data-event="click" data-month="0" data-year="2015"><a
									class="ui-state-default" href="#">31</a>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- input-group -->

		
	</div>
	 <script src="bootstrap/js/jquery-1.11.1.min.js"></script>
        <script src="bootstrap/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="bootstrap/js/jquery-ui-1.10.3.min.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <script src="bootstrap/js/modernizr.min.js"></script>
        <script src="bootstrap/js/pace.min.js"></script>
        <script src="bootstrap/js/retina.min.js"></script>
        <script src="bootstrap/js/jquery.cookies.js"></script>
        
        <script src="bootstrap/js/jquery.autogrow-textarea.js"></script>
        <script src="bootstrap/js/jquery.mousewheel.js"></script>
        <script src="bootstrap/js/jquery.tagsinput.min.js"></script>
        <script src="bootstrap/js/toggles.min.js"></script>
        <script src="bootstrap/js/bootstrap-timepicker.min.js"></script>
        <script src="bootstrap/js/jquery.maskedinput.min.js"></script>
        <script src="bootstrap/js/select2.min.js"></script>
        <script src="bootstrap/js/colorpicker.js"></script>
        <script src="bootstrap/js/dropzone.min.js"></script>

        <script src="bootstrap/js/custom.js"></script>
             <script>
            jQuery(document).ready(function() {
                
                // Tags Input
                jQuery('#tags').tagsInput({width:'auto'});
                 
                // Textarea Autogrow
                jQuery('#autoResizeTA').autogrow();
                
                // Spinner
                var spinner = jQuery('#spinner').spinner();
                spinner.spinner('value', 0);
                
                // Form Toggles
                jQuery('.toggle').toggles({on: true});
                
                // Time Picker
                jQuery('#timepicker').timepicker({defaultTIme: false});
                jQuery('#timepicker2').timepicker({showMeridian: false});
                jQuery('#timepicker3').timepicker({minuteStep: 15});
                
                // Date Picker
                jQuery('#datepicker').datepicker();
                jQuery('#datepicker-inline').datepicker();
                jQuery('#datepicker-multiple').datepicker({
                    numberOfMonths: 3,
                    showButtonPanel: true
                });
                
                // Input Masks
                jQuery("#date").mask("99/99/9999");
                jQuery("#phone").mask("(999) 999-9999");
                jQuery("#ssn").mask("999-99-9999");
                
                // Select2
                jQuery("#select-basic, #select-multi").select2();
                jQuery('#select-search-hide').select2({
                    minimumResultsForSearch: -1
                });
                
                function format(item) {
                    return '<i class="fa ' + ((item.element[0].getAttribute('rel') === undefined)?"":item.element[0].getAttribute('rel') ) + ' mr10"></i>' + item.text;
                }
                
                // This will empty first option in select to enable placeholder
                jQuery('select option:first-child').text('');
                
                jQuery("#select-templating").select2({
                    formatResult: format,
                    formatSelection: format,
                    escapeMarkup: function(m) { return m; }
                });
                
                // Color Picker
                if(jQuery('#colorpicker').length > 0) {
                    jQuery('#colorSelector').ColorPicker({
			onShow: function (colpkr) {
			    jQuery(colpkr).fadeIn(500);
                            return false;
			},
			onHide: function (colpkr) {
                            jQuery(colpkr).fadeOut(500);
                            return false;
			},
			onChange: function (hsb, hex, rgb) {
			    jQuery('#colorSelector span').css('backgroundColor', '#' + hex);
			    jQuery('#colorpicker').val('#'+hex);
			}
                    });
                }
  
                // Color Picker Flat Mode
                jQuery('#colorpickerholder').ColorPicker({
                    flat: true,
                    onChange: function (hsb, hex, rgb) {
			jQuery('#colorpicker3').val('#'+hex);
                    }
                });
                
                
            });
        </script>
</body>
</html>
