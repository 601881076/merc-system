package com.xhnj.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhnj.common.exception.BusinessException;
import com.xhnj.mapper.TDismissBatchCheckMapper;
import com.xhnj.mapper.TDismissBatchMapper;
import com.xhnj.model.TDismissBatch;
import com.xhnj.pojo.vo.AgreeDismissDetailVO;
import com.xhnj.service.TDismissBatchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 授权取消批次表 服务实现类
 * </p>
 *
 * @author lk
 * @since 2021-09-22
 */
@Service
public class TDismissBatchServiceImpl extends ServiceImpl<TDismissBatchMapper, TDismissBatch> implements TDismissBatchService {
    @Resource
    private TDismissBatchService dismissBatchService;

    @Resource
    private TDismissBatchCheckMapper dismissBatchCheckMapper;

    @Override
    public void exportExcel(HttpServletResponse response){
//        List<AgreeDismissDetailVO> list = new ArrayList<>();
//        list.stream().forEach(e ->e.setCardNo(businUtil.maskBankCard(e.getCardNo())));
        String fileName = "授权取消模板";
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls");
            ServletOutputStream out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            Sheet sheet = new Sheet(1,0, AgreeDismissDetailVO.class);
            //设置自适应宽度
            sheet.setAutoWidth(Boolean.TRUE);
            sheet.setSheetName("授权取消");
            writer.write(null,sheet);
            writer.finish();
            out.flush();
            response.getOutputStream().close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据batchNo修改 授权取消审核表状态
     * @param status 审核状态 0->待提交;1->已提交;2->审核通过;3->审核拒绝
     * @param batchNoList
     * @return
     */
    @Override
    public int updateStatusByBatchIdList(int status, List<String> batchNoList) {

        // 校验batchNoList需要修改的批次中是否存在已提交数据
        int i = dismissBatchCheckMapper.selectIsCommitData(batchNoList);
        if (0 < i)
            throw new BusinessException("批次已提交，请勿重复提交");

        return dismissBatchCheckMapper.updateByBatchIdList(status, batchNoList);
    }


}
