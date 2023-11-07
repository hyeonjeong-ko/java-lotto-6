package lotto.domain;

import java.util.List;

public class LottoResult {


    public long lottoNumbersPurchased(long userLottoPrice) {
        return userLottoPrice / 1000;
    }

    public void printRewardStatistics(Lottos purchasedLottos, List<Integer> winningNumbers, int bonusNumber) {
        System.out.println("당첨 통계");
        System.out.println("---");
        String[] rewardNames = {"3개", "4개", "5개", "5개 일치, 보너스 볼", "6개"};
        int[] reward = {5000,50000,1500000,30000000,2000000000};
        int [] rewardMatch = countMatchNumbers(purchasedLottos,winningNumbers,bonusNumber);

        for (int i = 0; i < rewardMatch.length; i++) {
            System.out.println(rewardNames[i] + " 일치 (" + String.format("%,d원", reward[i]) + ") - " + rewardMatch[i] + "개");
        }
    }

    /*
    * 로또 당첨 통계 계산
    * */
    private int[] countMatchNumbers(Lottos purchasedLottos, List<Integer> winningNumbers, int bonusNumber) {

        int[] rewardMatch = new int[4];

        for(Lotto lotto : purchasedLottos.getLottos()) {
            int matchCount = countMatchingNumbers(lotto,winningNumbers);
            if(matchCount >=3) {
                rewardMatch[matchCount-3]+=matchCount;
            }

            if (matchCount == 5 && lotto.getNumbers().contains(bonusNumber)) {
                    rewardMatch[3]++;
            }
        }
        return rewardMatch;
    }

    /*
    * 로또 번호 매칭 갯수
    * */
    private int countMatchingNumbers(Lotto lotto, List<Integer> winningNumbers) {
        return (int) lotto.getNumbers().stream()
                .filter(winningNumbers::contains)
                .count();
    }
}