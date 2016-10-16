[#escape x as x?html]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>${message("shop.member.deposit.recharge")}[#if showPowered] - Powered By TURINGROBOT[/#if]</title>
<meta name="author" content="TURINGROBOT Team" />
<meta name="copyright" content="TURINGROBOT" />
<link href="${base}/resources/shop/${theme}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/shop/${theme}/css/member.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/shop/${theme}/js/jquery.js"></script>
<script type="text/javascript" src="${base}/resources/shop/${theme}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/resources/shop/${theme}/js/common.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
	var $amount = $("#amount");
	var $paymentPluginId = $("#paymentPlugin input:radio");
	var $fee = $("#fee");
	var timeout;
	
	[@flash_message /]
	
	// 充值金额
	$amount.on("input propertychange change", function(event) {
		if (event.type != "propertychange" || event.originalEvent.propertyName == "value") {
			calculateFee();
		}
	});
	
	// 支付插件
	$paymentPluginId.click(function() {
		calculateFee();
	});
	
	// 计算支付手续费
	function calculateFee() {
		clearTimeout(timeout);
		timeout = setTimeout(function() {
			if ($inputForm.valid()) {
				var paymentPluginId = $paymentPluginId.filter(":checked").val();
				$.ajax({
					url: "calculate_fee.jhtml",
					type: "POST",
					data: {paymentPluginId: paymentPluginId, amount: $amount.val()},
					dataType: "json",
					cache: false,
					success: function(data) {
						if (data.message.type == "success") {
							if (data.fee > 0) {
								$fee.text(currency(data.fee, true)).closest("tr").show();
							} else {
								$fee.closest("tr").hide();
							}
						} else {
							$.message(data.message);
						}
					}
				});
			} else {
				$fee.closest("tr").hide();
			}
		}, 500);
	}
	
	// 检查余额
	setInterval(function() {
		$.ajax({
			url: "check_balance.jhtml",
			type: "POST",
			dataType: "json",
			cache: false,
			success: function(data) {
				if (data.balance > ${member.balance}) {
					location.href = "list.jhtml";
				}
			}
		});
	}, 10000);
	
	// 表单验证
	$inputForm.validate({
		rules: {
			amount: {
				required: true,
				positive: true,
				decimal: {
					integer: 12,
					fraction: ${setting.priceScale}
				}
			}
		},
		submitHandler: function(form) {
			form.submit();
		}
	});

});
</script>
</head>
<body>
	[#assign current = "depositRecharge" /]
	[#include "/shop/${theme}/include/header.ftl" /]
	<div class="container member">
		<div class="row">
			[#include "/shop/${theme}/member/include/navigation.ftl" /]
			<div class="span10">
				<div class="input deposit">
					<div class="title">${message("shop.member.deposit.recharge")}</div>
					<form id="inputForm" action="${base}/payment/plugin_submit.jhtml" method="post" target="_blank">
						<input type="hidden" name="type" value="recharge" />
						<table class="input">
							<tr>
								<th>
									${message("shop.member.deposit.balance")}:
								</th>
								<td>
									${currency(member.balance, true, true)}
								</td>
							</tr>
							<tr>
								<th>
									${message("shop.member.deposit.amount")}:
								</th>
								<td>
									<input type="text" id="amount" name="amount" class="text" maxlength="16" onpaste="return false;" />
								</td>
							</tr>
							<tr>
								<th>
									${message("shop.member.deposit.paymentPlugin")}:
								</th>
								<td>
									<div id="paymentPlugin" class="paymentPlugin clearfix">
										[#if paymentPlugins??]
											[#list paymentPlugins as paymentPlugin]
												<div>
													<input type="radio" id="${paymentPlugin.id}" name="paymentPluginId" value="${paymentPlugin.id}"[#if paymentPlugin == defaultPaymentPlugin] checked="checked"[/#if] />
													<label for="${paymentPlugin.id}">
														[#if paymentPlugin.logo?has_content]
															<em title="${paymentPlugin.paymentName}" style="background-image: url(${paymentPlugin.logo});">&nbsp;</em>
														[#else]
															<em>${paymentPlugin.paymentName}</em>
														[/#if]
													</label>
												</div>
											[/#list]
										[/#if]
									</div>
								</td>
							</tr>
							<tr class="hidden">
								<th>
									${message("shop.member.deposit.fee")}:
								</th>
								<td>
									<span id="fee"></span>
								</td>
							</tr>
							<tr>
								<th>
									&nbsp;
								</th>
								<td>
									<input type="submit" class="button" value="${message("shop.member.submit")}" />
									<input type="button" class="button" value="${message("shop.member.back")}" onclick="history.back(); return false;" />
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	[#include "/shop/${theme}/include/footer.ftl" /]
</body>
</html>
[/#escape]