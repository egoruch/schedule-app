package com.egor.schedule.app.fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.eb.schedule.shared.bean.HeroBean;
import com.eb.schedule.shared.bean.LeagueBean;
import com.eb.schedule.shared.bean.Match;
import com.eb.schedule.shared.bean.TeamBean;
import com.egor.schedule.app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.egor.schedule.app.utils.ImageUtils.BASE_URL;

/**
 * Created by Egor on 02.07.2016.
 */
public class MatchInfoFragment extends Fragment {

    private Match match;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.match = (Match) getArguments().getSerializable("match");
    }

    public static MatchInfoFragment newInstance(Match match) {
        MatchInfoFragment fragment = new MatchInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable("match", match);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.match_team_info, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView leagueName = (TextView) view.findViewById(R.id.league_name);
        LeagueBean league = match.getLeague();
        if (league != null) {
            leagueName.setText(league.getName());
        }

        TextView seriesType = (TextView) view.findViewById(R.id.series_type);
        String series = match.getSeriesType();
        if (series != null) {
            seriesType.setText(series);
        }


        TeamBean radiantTeam = match.getRadiantTeam();
        if (radiantTeam != null) {
            View radiantLogo = view.findViewById(R.id.radiant_logo);
            Picasso.with(getParentFragment().getActivity()).load(BASE_URL + radiantTeam.getLogo()).into((ImageView) radiantLogo);
            TextView radiantName = (TextView) view.findViewById(R.id.radiant_team_name);
            radiantName.setText(radiantTeam.getName());

            TextView score = (TextView) view.findViewById(R.id.match_score);
            score.setText(match.getMatchScore());
        }

        TeamBean direTeam = match.getDireTeam();
        if (direTeam != null) {
            View direLogo = view.findViewById(R.id.dire_logo);
            Picasso.with(getParentFragment().getActivity()).load(BASE_URL + direTeam.getLogo()).into((ImageView) direLogo);
            TextView direName = (TextView) view.findViewById(R.id.dire_team_name);
            direName.setText(direTeam.getName());
        }

        TextView duration = (TextView) view.findViewById(R.id.match_duration);
        if (match.getDuration() != null) {
            duration.setText(match.getDuration());
        }

        TextView netWorthAdv = (TextView) view.findViewById(R.id.net_worth_adv);
        List<Double> networthList = match.getNetworth();
        if (networthList != null && !networthList.isEmpty()) {
            //todo display number
            Double networth = networthList.get(networthList.size() - 1);
            Resources res = getResources();
            if (networth > 0) {
                netWorthAdv.setText(String.format(res.getString(R.string.net_worth_adv), match.getRadiantTeam().getName()));
            } else {
                netWorthAdv.setText(String.format(res.getString(R.string.net_worth_adv), match.getDireTeam().getName()));
            }
        }

//picks and bans
        initPicks(view, match.getRadianPicks(), "radiant_pick_");
        initPicks(view, match.getRadianBans(), "radiant_ban_");
        initPicks(view, match.getDirePicks(), "dire_pick_");
        initPicks(view, match.getDireBans(), "radiant_ban_");
    }

    private void initPicks(View view, List<HeroBean> heroes, String prefix) {
        if (heroes != null) {
            for (int i = 0; i < heroes.size(); i++) {
                ImageView pick = (ImageView) view.findViewById(getResources().getIdentifier(prefix + (i + 1), "id", getParentFragment().getActivity().getPackageName()));
                HeroBean hero = heroes.get(i);
                int drawableId = getResources().getIdentifier("h_" + hero.getId(), "drawable", getParentFragment().getActivity().getPackageName());
                pick.setImageResource(drawableId);
            }
        }
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        match = (Match) args.get("match");
    }
}
