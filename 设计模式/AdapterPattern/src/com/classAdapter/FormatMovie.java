package com.classAdapter;

//��������������ģʽ�ĺ���
/*
 * ����������ʽ��
 * ��ؼ�����extends���̳б���������
 */
public class FormatMovie extends PlayMP4 implements PlayMovie{

	@Override
	public void playMoive() {
		super.playMP4Movie();
	}
	
}
