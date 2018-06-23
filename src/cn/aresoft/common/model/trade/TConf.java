package cn.aresoft.common.model.trade;
import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
import com.puff.jdbc.core.Record;

import cn.aresoft.common.model.BaseModel;
@Table("t_conf")
public class TConf  extends BaseModel {
	/**
	 * 产品交易表
	 */
	private static final long serialVersionUID = 4283225245860013450L;

	@Column
	@PrimaryKey.IDWORKER
	private String conf_num;//确认编号;主键，流水号
	@Column
	private String conf_date;//确认日期;
	@Column
	private String cust_id;//客户编号
	@Column
	private String pro_id;//产品代码
	@Column
	private String busi_code;//业务代码;C01：购买  C02：赎回
	@Column
	private String req_nums;//原申请单编号，用于多笔同类业务合并，最多保留10笔，逗号分隔
	@Column
	private String req_date;//交易申请日期
	@Column
	private String opt_time;//操作时间
	@Column
	private Double conf_shares;//确认份额
	@Column
	private Double conf_amts;//确认金额
	@Column
	private String memo;//备注
	@Column
	private String channel_id;//渠道id
	@Column
	private String source;//来源
	@Column
	private String fund_contract;//基金合同附件
	@Column
	private String contract_num;//合同编号
	@Column(alias=true)
	private String pro_name;//产品名称
	@Column
	private String account_name;//客户名称
	@Column
	private String fundinfo_name;//产品名称
	@Column
	private String net_value;//成交单位净值
	@Column
	private String err_desc;//错误原因描述
	@Column
	private String conf_status;//交易状态  //0 未确认 1 已确认 2 客户撤单 3系统拒绝
	@Column
	private String trade_tate;//交易费率
	@Column
	private double trade_fee;//交易费
	@Column(alias=true)
	private Record fundinfo;
	@Column
	private String open_date;//净值开放日
	@Column
	private String oper_name;	//	联系人	
	@Column
	private String oper_tel	;//联系人电话

	public String getFundinfo_name() {
		return fundinfo_name;
	}
	public void setFundinfo_name(String fundinfo_name) {
		this.fundinfo_name = fundinfo_name;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getOper_name() {
		return oper_name;
	}
	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}
	public String getOper_tel() {
		return oper_tel;
	}
	public void setOper_tel(String oper_tel) {
		this.oper_tel = oper_tel;
	}
	public String getConf_num() {
		return conf_num;
	}
	public void setConf_num(String conf_num) {
		this.conf_num = conf_num;
	}
	public String getConf_date() {
		return conf_date;
	}
	public void setConf_date(String conf_date) {
		this.conf_date = conf_date;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public String getBusi_code() {
		return busi_code;
	}
	public void setBusi_code(String busi_code) {
		this.busi_code = busi_code;
	}
	public String getReq_nums() {
		return req_nums;
	}
	public void setReq_nums(String req_nums) {
		this.req_nums = req_nums;
	}
	public String getReq_date() {
		return req_date;
	}
	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}
	public String getOpt_time() {
		return opt_time;
	}
	public void setOpt_time(String opt_time) {
		this.opt_time = opt_time;
	}
	public Double getConf_shares() {
		return conf_shares;
	}
	public void setConf_shares(Double conf_shares) {
		this.conf_shares = conf_shares;
	}
	public Double getConf_amts() {
		return conf_amts;
	}
	public void setConf_amts(Double conf_amts) {
		this.conf_amts = conf_amts;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getContract_num() {
		return contract_num;
	}
	public void setContract_num(String contract_num) {
		this.contract_num = contract_num;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getNet_value() {
		return net_value;
	}
	public void setNet_value(String net_value) {
		this.net_value = net_value;
	}
	public String getFund_contract() {
		return fund_contract;
	}
	public void setFund_contract(String fund_contract) {
		this.fund_contract = fund_contract;
	}
	public String getErr_desc() {
		return err_desc;
	}
	public void setErr_desc(String err_desc) {
		this.err_desc = err_desc;
	}
	public String getTrade_tate() {
		return trade_tate;
	}
	public void setTrade_tate(String trade_tate) {
		this.trade_tate = trade_tate;
	}
	public double getTrade_fee() {
		return trade_fee;
	}
	public void setTrade_fee(double trade_fee) {
		this.trade_fee = trade_fee;
	}
	public String getConf_status() {
		return conf_status;
	}
	public void setConf_status(String conf_status) {
		this.conf_status = conf_status;
	}
	public Record getFundinfo() {
		return fundinfo;
	}
	public void setFundinfo(Record fundinfo) {
		this.fundinfo = fundinfo;
		
	}
	public String getOpen_date() {
		return open_date;
	}
	public void setOpen_date(String open_date) {
		this.open_date = open_date;
	}
	
	
}
