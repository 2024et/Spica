package Logic;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Beans.account_reportBeans;
import Beans.account_report_summaryBeans;
import Beans.balanceBeans;
import Beans.budget_reportBeans;
import Beans.categoryBeans;
import Dao.account_reportDao;
import Dao.financialDao;

public class account_report_detailLogic {
	
	//会計報告収支データの取得
	public List<balanceBeans> getAccountReportData(String id) {
		financialDao fi_dao = new financialDao();
		//範囲を取得
		account_reportDao acr_dao = new account_reportDao();
		//収支を取得
		account_reportBeans range = acr_dao.getRange(id);
		return fi_dao.getAccountReportData(range);
	}
	

	
	//カテゴリごとに集計
	public Map<String , Integer> totallingBalance(List<balanceBeans> balance, List<categoryBeans> category){
		Map<String, Integer> TotallingBalanceData = new LinkedHashMap<>();
		
		for(categoryBeans cData : category) {
			String targetCategory = cData.getName();
			int total = 0;
			for(balanceBeans bData : balance) {
				
				if(bData.getCategory().equals(targetCategory)) {
					total += bData.getAmount();
				}
			}
			System.out.println(targetCategory+"||"+total);
			TotallingBalanceData.put(targetCategory,total);
		}
		return TotallingBalanceData;
	}
	
	//収入集計
	public List<account_report_summaryBeans> totallingIncome(Map<String,Integer> balance, List<budget_reportBeans> budget, List<categoryBeans> category){
		List<account_report_summaryBeans> summaryData = new ArrayList<>();
		for(categoryBeans cData : category) {
			String targetCategory = cData.getName();
			System.out.println("taregt"+cData.getName());
			if(!"収入".equals(cData.getType())) {
				continue;
			} 
			int amount = balance.getOrDefault(targetCategory, 0);
			account_report_summaryBeans beans = new account_report_summaryBeans(
				targetCategory,
				0,
				amount,
				0
				);
			summaryData.add(beans);  
		}
		
		return summaryData;
	}
	
	//支出集計
		public List<account_report_summaryBeans> totallingExpend(Map<String,Integer> balance, List<budget_reportBeans> budget, List<categoryBeans> category){
			List<account_report_summaryBeans> summaryData = new ArrayList<>();
			for(categoryBeans cData : category) {
				String targetCategory = cData.getName();
				System.out.println("taregt"+cData.getName());
				if(!"支出".equals(cData.getType())) {
					continue;
				} 
				int amount = balance.getOrDefault(targetCategory, 0);
				for(budget_reportBeans bData : budget) {
					System.out.println("bData："+bData.getCategory_name());
					if(!targetCategory.equals(bData.getCategory_name())) {
						continue;
					}
					account_report_summaryBeans beans = new account_report_summaryBeans(
						targetCategory,						
						bData.getAmount(),
						amount,
						bData.getAmount() - amount
						);
					summaryData.add(beans);  
					System.out.println(targetCategory+"||"+amount+"||"+bData.getAmount());
					break;
				}
			}
			
			return summaryData;
		}
}
