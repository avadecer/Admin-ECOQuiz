package com.example.ecoquizadmin;

import static com.example.ecoquizadmin.QuestionActivity.quesList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<QuestionModel> ques_list;

    public QuestionAdapter(List<QuestionModel> ques_list) {
        this.ques_list = ques_list;
    }

    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_item_layout, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder viewHolder, int pos) {
        viewHolder.setData(pos, this);
    }

    @Override
    public int getItemCount() {
        return ques_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView deleteB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.catName);
            deleteB = itemView.findViewById(R.id.catDelB);

        }

        public void setData(final int pos, QuestionAdapter adapter) {
            title.setText("              QUESTION " + String.valueOf(pos + 1));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), QuestionDetailsActivity.class);
                    intent.putExtra("ACTION", "EDIT");
                    intent.putExtra("Q_ID", pos);
                    itemView.getContext().startActivity(intent);
                }
            });

            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog dialog = new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Delete Question")
                            .setMessage("Do you want to delete this question?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    deleteQuestion(pos, itemView.getContext(), adapter);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    dialog.getButton(dialog.BUTTON_POSITIVE).setBackgroundColor(Color.YELLOW);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setBackgroundColor(Color.YELLOW);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 50, 0);
                    dialog.getButton(dialog.BUTTON_NEGATIVE).setLayoutParams(params);
                }
            });
        }

        private void deleteQuestion(int pos, Context context, QuestionAdapter adapter) {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();


            firestore.collection("1").document(quesList.get(pos).getQuesID())
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Map<String, Object> quesDoc = new ArrayMap<>();
                            int index = 1;
                            for (int i = 0; i < quesList.size(); i++) {
                                if (i != pos) {
                                    quesDoc.put("Q" + String.valueOf(index) + "_ID", quesList.get(i).getQuesID());
                                    index++;
                                }
                            }

                            quesDoc.put("COUNT", String.valueOf(index - 1));

                            firestore.collection("1").document("QUESTIONS_LIST")
                                    .set(quesDoc)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(context, "Question Deleted Successfully", Toast.LENGTH_SHORT).show();

                                            quesList.remove(pos);
                                            adapter.notifyDataSetChanged();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    }
